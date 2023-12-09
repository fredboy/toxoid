@file:OptIn(ExperimentalSplittiesApi::class)

package ru.fredboy.toxoid.tox

import android.util.Log
import kotlinx.coroutines.*
import ru.fredboy.tox4a.api.core.ToxCore
import ru.fredboy.tox4a.api.core.callbacks.CompositeToxCoreEventListener
import ru.fredboy.tox4a.api.core.data.ToxPublicKey
import ru.fredboy.tox4a.api.core.data.enums.ToxConnection
import ru.fredboy.toxoid.clean.domain.model.BootstrapNode
import ru.fredboy.toxoid.utils.bytesToHexString
import splitties.coroutines.SuspendLazy
import splitties.experimental.ExperimentalSplittiesApi
import java.lang.Runnable
import java.util.*
import javax.inject.Inject

class ToxThread @Inject constructor(
    private val useCases: ToxServiceUseCases,
    private val toxCoreLazy: SuspendLazy<@JvmSuppressWildcards ToxCore>
) : Runnable {

    private val backgroundThread = Thread(this)
    private lateinit var toxCore: ToxCore

    fun startThread() {
        backgroundThread.start()
    }

    fun interruptThread() {
        backgroundThread.interrupt()
    }

    override fun run() {

        runBlocking {
            toxCore = toxCoreLazy()

            Log.d(TAG, "ToxCore initialized: ${bytesToHexString(toxCore.getAddress().value)}")


            val bootstrapNodesIterator = useCases.getSavedBootstrapNodes().iterator()

            Timer().scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    val thisTimerTask = this
                    when (useCases.getLatestSelfConnectionStatus()) {
                        ToxConnection.NONE -> {
                            if (!bootstrapNodesIterator.hasNext()) {
                                Log.e(TAG, "Failed bootstrapping")
                                thisTimerTask.cancel()
                            } else {
                                var isGoodNode = false
                                while (bootstrapNodesIterator.hasNext()) {
                                    val bootstrapNode = bootstrapNodesIterator.next()
                                    Log.d(TAG, "Trying ${bootstrapNode.host}")
                                    isGoodNode = toxCore.tryBootstrap(bootstrapNode)
                                    if (isGoodNode) {
                                        break
                                    }
                                }
                                if (!bootstrapNodesIterator.hasNext() && !isGoodNode) {
                                    Log.e(TAG, "Failed bootstrapping")
                                    thisTimerTask.cancel()
                                }
                            }
                        }
                        else -> {
                            thisTimerTask.cancel()
                        }
                    }
                }
            }, 0L, BOOTSTRAP_TIMEOUT)

            useCases.saveToxData(bytesToHexString(toxCore.getAddress().value), toxCore.getSaveData())
        }

        CoroutineScope(Dispatchers.Default).launch {
            useCases.getContactUpdatesFlow().collect(useCases::updateContact)
        }

        CoroutineScope(Dispatchers.Default).launch {
            useCases.getIncomingMessageFlow().collect(useCases::saveMessage)
        }

        val eventListener = CompositeToxCoreEventListener()
        while (!Thread.currentThread().isInterrupted) {
            Thread.sleep(toxCore.getIterationInterval().toLong())
            toxCore.iterate(eventListener)
        }

        Log.d(TAG, "dead")
    }

    private fun ToxCore.tryBootstrap(bootstrapNode: BootstrapNode): Boolean {
        return try {
            bootstrap(
                /* address = */ bootstrapNode.host,
                /* port = */ bootstrapNode.port,
                /* publicKey = */ ToxPublicKey(bootstrapNode.publicKey.bytes)
            )
            true
        } catch (e: /*ToxBootstrap*/Exception) {
            false
        }
    }

    companion object {
        private const val TAG = "ToxThread"

        private const val BOOTSTRAP_TIMEOUT = 20000L
    }

}
@file:OptIn(ExperimentalSplittiesApi::class)

package ru.fredboy.toxoid.tox

import android.util.Log
import im.tox.tox4j.core.ToxCore
import im.tox.tox4j.core.enums.ToxConnection
import im.tox.tox4j.core.exceptions.ToxBootstrapException
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
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

            Log.d(TAG, "ToxCore initialized: ${bytesToHexString(toxCore.address)}")


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

            useCases.saveToxData(bytesToHexString(toxCore.address), toxCore.savedata)
        }

        CoroutineScope(Dispatchers.Default).launch {
            useCases.getContactUpdatesFlow().collect(useCases::updateContact)
        }

        val eventListener = ToxEventListenerImpl(useCases)
        while (!Thread.currentThread().isInterrupted) {
            Thread.sleep(toxCore.iterationInterval().toLong())
            toxCore.iterate(eventListener, Any())
        }

        Log.d(TAG, "dead")
    }

    private fun ToxCore.tryBootstrap(bootstrapNode: BootstrapNode): Boolean {
        return try {
            bootstrap(
                bootstrapNode.host,
                bootstrapNode.port,
                bootstrapNode.publicKey.bytes
            )
            true
        } catch (e: ToxBootstrapException) {
            false
        }
    }

    companion object {
        private const val TAG = "ToxThread"

        private const val BOOTSTRAP_TIMEOUT = 20000L
    }

}
package ru.fredboy.toxoid.tox

import android.content.Context
import android.util.Log
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import im.tox.tox4j.core.ToxCore
import im.tox.tox4j.core.enums.ToxConnection
import im.tox.tox4j.core.exceptions.ToxBootstrapException
import im.tox.tox4j.core.options.ToxOptions
import im.tox.tox4j.impl.jni.ToxCoreImpl
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ru.fredboy.toxoid.clean.domain.model.BootstrapNode
import ru.fredboy.toxoid.utils.bytesToHexString
import java.io.IOException
import java.lang.Runnable
import java.util.*
import javax.inject.Inject
import kotlin.NoSuchElementException

class ToxThread @Inject constructor(
    @ApplicationContext private val context: Context,
    private val useCases: ToxServiceUseCases
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
            val toxOptions = try {
                tryLoadDataForCurrentUser()
            } catch (e: Exception) {
                useCases.createNewToxOptions()
            }

            toxCore = ToxCoreImpl(toxOptions)

            useCases.setOwnToxId(toxCore.address)
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

        CoroutineScope(Dispatchers.Main).launch {
            useCases.getSelfConnectionStatusFlow()
                .flowOn(Dispatchers.IO)
                .collect {
                    Toast.makeText(
                        context,
                        "Connection status: ${it.name}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }

        val eventListener = ToxEventListenerImpl(useCases)
        while (!Thread.currentThread().isInterrupted) {
            Thread.sleep(toxCore.iterationInterval().toLong())
            toxCore.iterate(eventListener, Any())
        }

        Log.d(TAG, "dead")
    }

    private suspend fun tryLoadDataForCurrentUser(): ToxOptions {
        val currentUser = useCases.getCurrentUser()
            ?: throw IllegalStateException("No current user")

        return useCases.loadToxData(currentUser.id)
    }

    private fun ToxCore.tryBootstrap(bootstrapNode: BootstrapNode): Boolean {
        return try {
            bootstrap(
                bootstrapNode.host,
                bootstrapNode.port,
                bootstrapNode.publicKey.value()
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
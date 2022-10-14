package ru.fredboy.toxoid.tox

import android.content.Context
import android.util.Log
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import im.tox.tox4j.core.ToxCore
import im.tox.tox4j.impl.jni.ToxCoreImpl
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import ru.fredboy.toxoid.utils.bytesToHexString
import java.io.IOException
import java.lang.Runnable
import javax.inject.Inject

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
            val currentUser = useCases.getCurrentUser()

            val toxOptions = try {
                currentUser?.id?.let { toxId ->
                    useCases.loadToxData(toxId)
                } ?: useCases.createNewToxOptions()
            } catch (e: IOException) {
                useCases.createNewToxOptions()
            }

            toxCore = ToxCoreImpl(toxOptions)

            useCases.setOwnToxId(toxCore.address)
            Log.d(TAG, "ToxCore initialized: ${bytesToHexString(toxCore.address)}")


            val bootstrapNodes = useCases.getSavedBootstrapNodes()
            val bootstrapNode = bootstrapNodes.first()

            toxCore.bootstrap(
                bootstrapNode.host,
                bootstrapNode.port,
                bootstrapNode.publicKey.value()
            )

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

    companion object {
        private const val TAG = "ToxThread"
    }

}
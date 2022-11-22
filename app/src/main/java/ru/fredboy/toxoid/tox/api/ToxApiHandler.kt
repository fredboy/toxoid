@file:OptIn(ExperimentalSplittiesApi::class)

package ru.fredboy.toxoid.tox.api

import android.os.Bundle
import dagger.hilt.android.scopes.ServiceScoped
import im.tox.tox4j.core.ToxCore
import kotlinx.coroutines.runBlocking
import ru.fredboy.toxoid.R
import ru.fredboy.toxoid.clean.data.model.intent.*
import ru.fredboy.toxoid.clean.data.model.intent.args.*
import ru.fredboy.toxoid.clean.data.source.intent.*
import ru.fredboy.toxoid.tox.api.methods.*
import splitties.coroutines.SuspendLazy
import splitties.experimental.ExperimentalSplittiesApi
import java.util.*
import javax.inject.Inject

@ServiceScoped
class ToxApiHandler @Inject constructor(
    private val toxCoreLazy: SuspendLazy<@JvmSuppressWildcards ToxCore>,
) {

    private val methodQueue = ToxServiceApiMethodQueue()
    private val executorThread: Thread

    init {
        executorThread = object : Thread() {
            override fun run() {
                while (!this.isInterrupted) {
                    try {
                        val method = methodQueue.take()
                        runBlocking {
                            toxCoreLazy.execute { toxCore ->
                                val result = method.execute(toxCore)
                                method.resultReceiver.send(
                                    R.id.result_code_okay,
                                    Bundle().apply { putParcelable(EXTRA_RESULT, result) }
                                )
                            }
                        }
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    fun handleAction(data: Bundle) {
        val method = getMethod(data.getArgs())
        methodQueue.add(method)
    }

    fun start() {
        executorThread.start()
    }

    fun interrupt() {
        executorThread.interrupt()
    }

    private fun getMethod(args: ToxServiceArgs): ToxServiceGenericApiMethod {
        @Suppress("UNCHECKED_CAST")
        return when (args) {
            is ToxServiceSendMessageArgs -> SendMessageMethod(args)
            is ToxServiceAddFriendArgs -> AddFriendMethod(args)
            is ToxServiceGetOwnAddressArgs -> GetOwnToxAddressMethod(args)
            is ToxServiceResolveFriendNumberArgs -> ResolveFriendNumberMethod(args)
        } as ToxServiceGenericApiMethod
    }

    private fun Bundle.getArgs(): ToxServiceArgs {
        return getParcelable(EXTRA_ARGUMENTS, ToxServiceArgs::class.java)
            ?: throw IllegalStateException()
    }

    private suspend fun <T> SuspendLazy<ToxCore>.execute(
        block: suspend (toxCore: ToxCore) -> T
    ): T {
        return block(this())
    }
}
@file:OptIn(ExperimentalSplittiesApi::class)

package ru.fredboy.toxoid.tox.api

import android.os.Bundle
import dagger.hilt.android.scopes.ServiceScoped
import im.tox.tox4j.core.ToxCore
import kotlinx.coroutines.runBlocking
import ru.fredboy.toxoid.R
import ru.fredboy.toxoid.clean.data.model.intent.*
import ru.fredboy.toxoid.clean.data.model.intent.args.ToxServiceAddFriendArgs
import ru.fredboy.toxoid.clean.data.model.intent.args.ToxServiceArgs
import ru.fredboy.toxoid.clean.data.model.intent.args.ToxServiceGetOwnAddressArgs
import ru.fredboy.toxoid.clean.data.model.intent.args.ToxServiceSendMessageArgs
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

    fun handleAction(action: String, data: Bundle) {
        val args: ToxServiceArgs = extractArgs(action, data)
        val method = getMethod(args)
        methodQueue.add(method)
    }

    fun start() {
        executorThread.start()
    }

    fun interrupt() {
        executorThread.interrupt()
    }

    private fun extractArgs(action: String, data: Bundle): ToxServiceArgs {
        return when (action) {
            ACTION_SEND_MESSAGE -> data.getArgs(
                parcelKey = ToxServiceSendMessageArgs.PARCEL_KEY,
                type = ToxServiceSendMessageArgs::class.java
            )
            ACTION_ADD_FRIEND -> data.getArgs(
                parcelKey = ToxServiceAddFriendArgs.PARCEL_KEY,
                type = ToxServiceAddFriendArgs::class.java
            )
            ACTION_GET_OWN_ADDRESS -> data.getArgs(
                parcelKey = ToxServiceGetOwnAddressArgs.PARCEL_KEY,
                type = ToxServiceGetOwnAddressArgs::class.java
            )
            else -> throw IllegalArgumentException("Unknown action: $action")
        }
    }

    private fun getMethod(args: ToxServiceArgs): ToxServiceGenericApiMethod {
        @Suppress("UNCHECKED_CAST")
        return when (args) {
            is ToxServiceSendMessageArgs -> SendMessageMethod(args)
            is ToxServiceAddFriendArgs -> AddFriendMethod(args)
            is ToxServiceGetOwnAddressArgs -> GetOwnToxAddressMethod(args)
        } as ToxServiceGenericApiMethod
    }

    private fun <T> Bundle.getArgs(parcelKey: String, type: Class<T>): T {
        return getParcelable(parcelKey, type) ?: throw IllegalArgumentException()
    }

    private suspend fun <T> SuspendLazy<ToxCore>.execute(
        block: suspend (toxCore: ToxCore) -> T
    ): T {
        return block(this())
    }
}
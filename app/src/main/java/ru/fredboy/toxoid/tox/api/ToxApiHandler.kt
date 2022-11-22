@file:OptIn(ExperimentalSplittiesApi::class)

package ru.fredboy.toxoid.tox.api

import android.os.Bundle
import android.os.ResultReceiver
import im.tox.tox4j.core.ToxCore
import im.tox.tox4j.core.ToxCoreConstants
import im.tox.tox4j.core.enums.ToxMessageType
import im.tox.tox4j.core.exceptions.ToxFriendAddException
import im.tox.tox4j.core.exceptions.ToxFriendByPublicKeyException
import im.tox.tox4j.core.exceptions.ToxFriendSendMessageException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.fredboy.toxoid.R
import ru.fredboy.toxoid.clean.data.model.intent.*
import ru.fredboy.toxoid.clean.data.source.intent.*
import ru.fredboy.toxoid.clean.domain.model.ToxAddress
import ru.fredboy.toxoid.utils.bytesToHexString
import ru.fredboy.toxoid.utils.exhaustive
import splitties.coroutines.SuspendLazy
import splitties.experimental.ExperimentalSplittiesApi
import java.util.*
import javax.inject.Inject

class ToxApiHandler @Inject constructor(
    private val useCases: ToxApiHandlerUseCases,
    private val toxCoreLazy: SuspendLazy<@JvmSuppressWildcards ToxCore>,
) {

    fun handleAction(action: String, data: Bundle) {
        CoroutineScope(Dispatchers.Main).launch {
            val args: ToxServiceArgs = extractArgs(action, data)

            when (args) {
                is ToxServiceSendMessageArgs -> sendMessage(args)
                is ToxServiceAddFriendArgs -> sendFriendRequest(args)
                is ToxServiceGetOwnAddressArgs -> getOwnToxAddress(args, data.getResultReceiver())
            }.exhaustive()
        }
    }

    private suspend fun sendMessage(args: ToxServiceSendMessageArgs) =
        toxCoreLazy.execute { toxCore ->
            val recipientNumber = try {
                toxCore.friendByPublicKey(args.recipientPublicKeyBytes)
            } catch (e: ToxFriendByPublicKeyException) {
                e.printStackTrace()
                return@execute
            }

            val delta = (System.currentTimeMillis() - args.timestamp).toInt()

            args.messageBytes
                .asSequence()
                .chunked(ToxCoreConstants.MaxMessageLength())
                .map { it.toByteArray() }
                .forEach { messageChunkBytes ->
                    try {
                        toxCore.friendSendMessage(
                            /* friendNumber = */ recipientNumber,
                            /* messageType = */ ToxMessageType.NORMAL,
                            /* timeDelta = */ delta,
                            /* message = */ messageChunkBytes
                        )
                    } catch (e: ToxFriendSendMessageException) {
                        e.printStackTrace()
                        return@execute
                    }
                }
        }

    private suspend fun sendFriendRequest(args: ToxServiceAddFriendArgs) =
        toxCoreLazy.execute { toxCore ->
            try {
                toxCore.addFriend(
                    /* address = */ args.friendAddressBytes,
                    /* message = */ args.messageBytes.sliceFriendRequestMessage()
                )
                useCases.saveToxData(bytesToHexString(toxCore.address), toxCore.savedata)
            } catch (e: Exception) {
                when (e) {
                    is ToxFriendAddException, is IllegalArgumentException -> e.printStackTrace()
                    else -> throw e
                }
            }
        }

    private suspend fun getOwnToxAddress(
        args: ToxServiceGetOwnAddressArgs,
        resultReceiver: ResultReceiver
    ) =
        toxCoreLazy.execute { toxCore ->
            resultReceiver.send(R.id.result_code_okay, Bundle().apply {
                putParcelable(
                    ToxServiceGetOwnAddressResult.PARCEL_KEY, ToxServiceGetOwnAddressResult(
                        ToxAddress(toxCore.address)
                    )
                )
            })
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

    private fun <T> Bundle.getArgs(parcelKey: String, type: Class<T>): T {
        return getParcelable(parcelKey, type) ?: throw IllegalArgumentException()
    }

    private fun Bundle.getResultReceiver(): ResultReceiver {
        return getParcelable(EXTRA_CALLBACK, ResultReceiver::class.java)
            ?: throw IllegalStateException("No result receiver")
    }

    private suspend fun <T> SuspendLazy<ToxCore>.execute(
        block: suspend (toxCore: ToxCore) -> T
    ): T {
        return block(this())
    }

    private fun ByteArray.sliceFriendRequestMessage(): ByteArray {
        return if (size > ToxCoreConstants.MaxFriendRequestLength()) {
            sliceArray(0 until ToxCoreConstants.MaxFriendRequestLength())
        } else {
            this
        }
    }
}
package ru.fredboy.toxoid.tox.api.methods

import im.tox.tox4j.core.ToxCore
import im.tox.tox4j.core.ToxCoreConstants
import im.tox.tox4j.core.data.ToxFriendMessage
import im.tox.tox4j.core.data.ToxPublicKey
import im.tox.tox4j.core.enums.ToxMessageType
import ru.fredboy.toxoid.clean.data.model.intent.args.ToxServiceSendMessageArgs
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceErrorResult
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceResult
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceSendMessageResult
import ru.fredboy.toxoid.utils.rightOrThrow

class SendMessageMethod(
    args: ToxServiceSendMessageArgs
) : ToxServiceApiMethod<ToxServiceSendMessageArgs>(args) {

    override suspend fun execute(toxCore: ToxCore): ToxServiceResult {
        val recipientNumber = try {
            toxCore.friendByPublicKey(
                ToxPublicKey.fromValue(args.recipientPublicKeyBytes).rightOrThrow()
            )
        } catch (e: Exception) {
            return ToxServiceErrorResult(e)
        }

        val delta = (System.currentTimeMillis() - args.timestamp).toInt()

        args.messageBytes
            .asSequence()
            .chunked(ToxCoreConstants.maxMessageLength)
            .map { it.toByteArray() }
            .forEach { messageChunkBytes ->
                try {
                    toxCore.friendSendMessage(
                        friendNumber = recipientNumber,
                        messageType = ToxMessageType.NORMAL,
                        timeDelta = delta,
                        message = ToxFriendMessage.fromValue(messageChunkBytes).rightOrThrow()
                    )
                } catch (e: Exception) {
                    return ToxServiceErrorResult(e)
                }
            }

        return ToxServiceSendMessageResult
    }

}
package ru.fredboy.toxoid.tox.api.methods

import ru.fredboy.tox4a.api.core.ToxCore
import ru.fredboy.tox4a.api.core.ToxCoreConstants
import ru.fredboy.tox4a.api.core.data.ToxFriendMessage
import ru.fredboy.tox4a.api.core.data.ToxPublicKey
import ru.fredboy.tox4a.api.core.data.enums.ToxMessageType
import ru.fredboy.toxoid.clean.data.model.intent.args.ToxServiceSendMessageArgs
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceErrorResult
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceResult
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceSendMessageResult

class SendMessageMethod(
    args: ToxServiceSendMessageArgs
) : ToxServiceApiMethod<ToxServiceSendMessageArgs>(args) {

    override suspend fun execute(toxCore: ToxCore): ToxServiceResult {
        val recipientNumber = try {
            toxCore.friendByPublicKey(ToxPublicKey(args.recipientPublicKeyBytes))
        } catch (e: /*ToxFriendByPublicKey*/Exception) {
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
                        message = ToxFriendMessage(messageChunkBytes)
                    )
                } catch (e: /*ToxFriendSendMessage*/Exception) {
                    return ToxServiceErrorResult(e)
                }
            }

        return ToxServiceSendMessageResult
    }

}
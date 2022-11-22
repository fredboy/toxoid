package ru.fredboy.toxoid.tox.api.methods

import im.tox.tox4j.core.ToxCore
import im.tox.tox4j.core.ToxCoreConstants
import im.tox.tox4j.core.enums.ToxMessageType
import im.tox.tox4j.core.exceptions.ToxFriendByPublicKeyException
import im.tox.tox4j.core.exceptions.ToxFriendSendMessageException
import ru.fredboy.toxoid.clean.data.model.intent.args.ToxServiceSendMessageArgs
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceErrorResult
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceResult
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceSendMessageResult

class SendMessageMethod(
    args: ToxServiceSendMessageArgs
) : ToxServiceApiMethod<ToxServiceSendMessageArgs>(args) {

    override suspend fun execute(toxCore: ToxCore): ToxServiceResult {
        val recipientNumber = try {
            toxCore.friendByPublicKey(args.recipientPublicKeyBytes)
        } catch (e: ToxFriendByPublicKeyException) {
            return ToxServiceErrorResult(e)
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
                    return ToxServiceErrorResult(e)
                }
            }

        return ToxServiceSendMessageResult
    }

}
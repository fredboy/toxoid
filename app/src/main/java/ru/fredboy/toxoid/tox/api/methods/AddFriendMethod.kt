package ru.fredboy.toxoid.tox.api.methods

import im.tox.tox4j.core.ToxCore
import im.tox.tox4j.core.ToxCoreConstants
import ru.fredboy.toxoid.clean.data.model.intent.args.ToxServiceAddFriendArgs
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceAddFriendResult

class AddFriendMethod(
    args: ToxServiceAddFriendArgs,
) : ToxServiceApiMethod<ToxServiceAddFriendArgs, ToxServiceAddFriendResult>(args) {

    override suspend fun execute(toxCore: ToxCore): ToxServiceAddFriendResult {
        try {
            toxCore.addFriend(
                /* address = */ args.friendAddressBytes,
                /* message = */ args.messageBytes.sliceFriendRequestMessage()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return ToxServiceAddFriendResult(error = e)
        }

        return ToxServiceAddFriendResult()
    }

    private fun ByteArray.sliceFriendRequestMessage(): ByteArray {
        return if (size > ToxCoreConstants.MaxFriendRequestLength()) {
            sliceArray(0 until ToxCoreConstants.MaxFriendRequestLength())
        } else {
            this
        }
    }

}
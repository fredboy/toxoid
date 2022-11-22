package ru.fredboy.toxoid.tox.api.methods

import im.tox.tox4j.core.ToxCore
import im.tox.tox4j.core.ToxCoreConstants
import ru.fredboy.toxoid.clean.data.model.intent.args.ToxServiceAddFriendArgs
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceAddFriendResult
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceErrorResult
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceResult
import ru.fredboy.toxoid.clean.data.model.tox.ToxSaveData

class AddFriendMethod(
    args: ToxServiceAddFriendArgs,
) : ToxServiceApiMethod<ToxServiceAddFriendArgs>(args) {

    override suspend fun execute(toxCore: ToxCore): ToxServiceResult {
        try {
            toxCore.addFriend(
                /* address = */ args.friendAddressBytes,
                /* message = */ args.messageBytes.sliceFriendRequestMessage()
            )
        } catch (e: Exception) {
            return ToxServiceErrorResult(e)
        }

        return ToxServiceAddFriendResult(
            toxSaveData = ToxSaveData(
                ownAddressBytes = toxCore.address,
                toxSaveData = toxCore.savedata
            )
        )
    }

    private fun ByteArray.sliceFriendRequestMessage(): ByteArray {
        return if (size > ToxCoreConstants.MaxFriendRequestLength()) {
            sliceArray(0 until ToxCoreConstants.MaxFriendRequestLength())
        } else {
            this
        }
    }

}
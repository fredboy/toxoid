package ru.fredboy.toxoid.tox.api.methods

import ru.fredboy.tox4a.api.core.ToxCore
import ru.fredboy.tox4a.api.core.ToxCoreConstants
import ru.fredboy.tox4a.api.core.data.ToxFriendAddress
import ru.fredboy.tox4a.api.core.data.ToxFriendRequestMessage
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
                /* address = */ ToxFriendAddress(args.friendAddressBytes),
                /* message = */ ToxFriendRequestMessage(args.messageBytes.sliceFriendRequestMessage())
            )
        } catch (e: Exception) {
            return ToxServiceErrorResult(e)
        }

        return ToxServiceAddFriendResult(
            toxSaveData = ToxSaveData(
                ownAddressBytes = toxCore.getAddress().value,
                toxSaveData = toxCore.getSaveData()
            )
        )
    }

    private fun ByteArray.sliceFriendRequestMessage(): ByteArray {
        return if (size > ToxCoreConstants.maxFriendRequestLength) {
            sliceArray(0 until ToxCoreConstants.maxFriendRequestLength)
        } else {
            this
        }
    }

}
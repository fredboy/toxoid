package ru.fredboy.toxoid.tox.api.methods

import im.tox.tox4j.core.ToxCore
import im.tox.tox4j.core.ToxCoreConstants
import im.tox.tox4j.core.data.ToxFriendAddress
import im.tox.tox4j.core.data.ToxFriendRequestMessage
import ru.fredboy.toxoid.clean.data.model.intent.args.ToxServiceAddFriendArgs
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceAddFriendResult
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceErrorResult
import ru.fredboy.toxoid.clean.data.model.intent.result.ToxServiceResult
import ru.fredboy.toxoid.clean.data.model.tox.ToxSaveData
import ru.fredboy.toxoid.utils.rightOrThrow

class AddFriendMethod(
    args: ToxServiceAddFriendArgs,
) : ToxServiceApiMethod<ToxServiceAddFriendArgs>(args) {

    override suspend fun execute(toxCore: ToxCore): ToxServiceResult {
        try {
            toxCore.addFriend(
                /* address = */ ToxFriendAddress.fromValue(args.friendAddressBytes).rightOrThrow(),
                /* message = */ ToxFriendRequestMessage.fromValue(args.messageBytes.sliceFriendRequestMessage()).rightOrThrow()
            )
        } catch (e: Exception) {
            return ToxServiceErrorResult(e)
        }

        return ToxServiceAddFriendResult(
            toxSaveData = ToxSaveData(
                ownAddressBytes = toxCore.address.value,
                toxSaveData = toxCore.saveData
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
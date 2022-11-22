package ru.fredboy.toxoid.clean.data.mapper

import ru.fredboy.toxoid.clean.data.model.intent.ToxServiceAddFriendArgs
import ru.fredboy.toxoid.clean.domain.model.FriendRequest
import javax.inject.Inject

class ToxServiceAddFriendArgsMapper @Inject constructor() {

    fun map(friendRequest: FriendRequest): ToxServiceAddFriendArgs {
        return ToxServiceAddFriendArgs(
            friendAddressBytes = friendRequest.friendAddress.bytes,
            messageBytes = friendRequest.message.toByteArray(),
        )
    }

}
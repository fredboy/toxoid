package ru.fredboy.tox4a.core.callbacks

import ru.fredboy.tox4a.core.data.ToxFriendNumber
import ru.fredboy.tox4a.core.data.ToxLossyPacket

fun interface FriendLossyPacketCallback {

    fun friendLossyPacket(
        toxFriendNumber: ToxFriendNumber,
        data: ToxLossyPacket
    )

}
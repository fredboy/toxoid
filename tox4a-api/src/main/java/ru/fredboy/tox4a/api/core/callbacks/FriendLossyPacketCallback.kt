package ru.fredboy.tox4a.api.core.callbacks

import ru.fredboy.tox4a.api.core.data.ToxFriendNumber
import ru.fredboy.tox4a.api.core.data.ToxLossyPacket

fun interface FriendLossyPacketCallback {

    fun friendLossyPacket(
        toxFriendNumber: ToxFriendNumber,
        data: ToxLossyPacket
    )

}
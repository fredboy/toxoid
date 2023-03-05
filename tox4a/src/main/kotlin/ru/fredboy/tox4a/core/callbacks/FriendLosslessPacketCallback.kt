package ru.fredboy.tox4a.core.callbacks

import ru.fredboy.tox4a.core.data.ToxFriendNumber
import ru.fredboy.tox4a.core.data.ToxLosslessPacket

fun interface FriendLosslessPacketCallback {

    fun friendLosslessPacket(
        toxFriendNumber: ToxFriendNumber,
        data: ToxLosslessPacket
    )

}
package ru.fredboy.tox4a.api.core.callbacks

import ru.fredboy.tox4a.api.core.data.ToxFriendNumber
import ru.fredboy.tox4a.api.core.data.ToxLosslessPacket

fun interface FriendLosslessPacketCallback {

    fun friendLosslessPacket(
        toxFriendNumber: ToxFriendNumber,
        data: ToxLosslessPacket
    )

}
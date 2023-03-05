package ru.fredboy.tox4a.core.callbacks

import ru.fredboy.tox4a.core.data.ToxFriendNumber
import ru.fredboy.tox4a.core.data.enums.ToxConnection

fun interface FriendConnectionStatusCallback {

    fun friendConnectionStatus(
        toxFriendNumber: ToxFriendNumber,
        connectionStatus: ToxConnection
    )

}
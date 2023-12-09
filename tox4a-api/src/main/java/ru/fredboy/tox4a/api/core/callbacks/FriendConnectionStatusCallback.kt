package ru.fredboy.tox4a.api.core.callbacks

import ru.fredboy.tox4a.api.core.data.ToxFriendNumber
import ru.fredboy.tox4a.api.core.data.enums.ToxConnection

fun interface FriendConnectionStatusCallback {

    fun friendConnectionStatus(
        toxFriendNumber: ToxFriendNumber,
        connectionStatus: ToxConnection
    )

}
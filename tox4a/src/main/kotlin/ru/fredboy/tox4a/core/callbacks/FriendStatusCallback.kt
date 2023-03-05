package ru.fredboy.tox4a.core.callbacks

import ru.fredboy.tox4a.core.data.ToxFriendNumber
import ru.fredboy.tox4a.core.data.enums.ToxUserStatus

fun interface FriendStatusCallback {

    fun friendStatus(
        toxFriendNumber: ToxFriendNumber,
        status: ToxUserStatus
    )

}
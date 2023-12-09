package ru.fredboy.tox4a.api.core.callbacks

import ru.fredboy.tox4a.api.core.data.ToxFriendNumber
import ru.fredboy.tox4a.api.core.data.enums.ToxUserStatus

fun interface FriendStatusCallback {

    fun friendStatus(
        toxFriendNumber: ToxFriendNumber,
        status: ToxUserStatus
    )

}
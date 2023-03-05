package ru.fredboy.tox4a.core.callbacks

import ru.fredboy.tox4a.core.data.ToxFriendNumber
import ru.fredboy.tox4a.core.data.ToxStatusMessage

fun interface FriendStatusMessageCallback {

    fun friendStatusMessage(
        toxFriendNumber: ToxFriendNumber,
        message: ToxStatusMessage
    )

}
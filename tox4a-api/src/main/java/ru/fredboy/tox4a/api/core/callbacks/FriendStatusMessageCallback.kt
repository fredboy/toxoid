package ru.fredboy.tox4a.api.core.callbacks

import ru.fredboy.tox4a.api.core.data.ToxFriendNumber
import ru.fredboy.tox4a.api.core.data.ToxStatusMessage

fun interface FriendStatusMessageCallback {

    fun friendStatusMessage(
        toxFriendNumber: ToxFriendNumber,
        message: ToxStatusMessage
    )

}
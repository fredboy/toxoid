package ru.fredboy.tox4a.core.callbacks

import ru.fredboy.tox4a.core.data.ToxFriendMessage
import ru.fredboy.tox4a.core.data.ToxFriendNumber
import ru.fredboy.tox4a.core.data.enums.ToxMessageType

fun interface FriendMessageCallback {

    fun friendMessage(
        toxFriendNumber: ToxFriendNumber,
        messageType: ToxMessageType,
        timeDelta: Int,
        message: ToxFriendMessage
    )

}
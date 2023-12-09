package ru.fredboy.tox4a.api.core.callbacks

import ru.fredboy.tox4a.api.core.data.ToxFriendNumber

fun interface FriendTypingCallback {

    fun friendTyping(
        toxFriendNumber: ToxFriendNumber,
        isTyping: Boolean
    )

}
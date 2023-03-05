package ru.fredboy.tox4a.core.callbacks

import ru.fredboy.tox4a.core.data.ToxFriendNumber

fun interface FriendTypingCallback {

    fun friendTyping(
        toxFriendNumber: ToxFriendNumber,
        isTyping: Boolean
    )

}
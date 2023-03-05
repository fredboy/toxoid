package ru.fredboy.tox4a.core.callbacks

import ru.fredboy.tox4a.core.data.ToxFriendNumber
import ru.fredboy.tox4a.core.data.ToxNickname

fun interface FriendNameCallback {

    fun friendName(
        toxFriendNumber: ToxFriendNumber,
        name: ToxNickname
    )

}
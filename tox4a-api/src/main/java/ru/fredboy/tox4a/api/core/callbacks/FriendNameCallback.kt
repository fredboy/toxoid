package ru.fredboy.tox4a.api.core.callbacks

import ru.fredboy.tox4a.api.core.data.ToxFriendNumber
import ru.fredboy.tox4a.api.core.data.ToxNickname

fun interface FriendNameCallback {

    fun friendName(
        toxFriendNumber: ToxFriendNumber,
        name: ToxNickname
    )

}
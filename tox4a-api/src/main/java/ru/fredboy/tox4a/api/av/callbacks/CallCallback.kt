package ru.fredboy.tox4a.api.av.callbacks

import ru.fredboy.tox4a.api.core.data.ToxFriendNumber

fun interface CallCallback {

    fun call(friendNumber: ToxFriendNumber, audioEnabled: Boolean, videoEnabled: Boolean)

}
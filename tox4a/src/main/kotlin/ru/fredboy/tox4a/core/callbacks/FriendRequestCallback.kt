package ru.fredboy.tox4a.core.callbacks

import ru.fredboy.tox4a.core.data.ToxFriendRequestMessage
import ru.fredboy.tox4a.core.data.ToxPublicKey

fun interface FriendRequestCallback {

    fun friendRequest(
        publicKey: ToxPublicKey,
        timeDelta: Int,
        message: ToxFriendRequestMessage
    )

}
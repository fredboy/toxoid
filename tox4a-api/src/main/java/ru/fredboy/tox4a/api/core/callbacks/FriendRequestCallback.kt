package ru.fredboy.tox4a.api.core.callbacks

import ru.fredboy.tox4a.api.core.data.ToxFriendRequestMessage
import ru.fredboy.tox4a.api.core.data.ToxPublicKey

fun interface FriendRequestCallback {

    fun friendRequest(
        publicKey: ToxPublicKey,
        timeDelta: Int,
        message: ToxFriendRequestMessage
    )

}
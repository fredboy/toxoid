package ru.fredboy.toxoid.clean.domain.model

import im.tox.tox4j.core.data.ToxPublicKey

data class FriendRequest(
    val publicKey: ToxPublicKey,
    val message: String
)

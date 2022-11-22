package ru.fredboy.toxoid.clean.domain.model

data class FriendRequest(
    val friendAddress: ToxAddress,
    val message: String
)

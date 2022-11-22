package ru.fredboy.toxoid.clean.domain.model

data class FriendRequest(
    val friendAddress: ToxFriendAddress,
    val message: String
)

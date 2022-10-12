package ru.fredboy.toxoid.clean.data.source.tox

import ru.fredboy.toxoid.clean.data.model.room.FriendRequestEntity

interface CachedFriendRequestDataSource {

    suspend fun getAll(): List<FriendRequestEntity>

    suspend fun getById(toxId: String): FriendRequestEntity?

    suspend fun add(friendRequestEntity: FriendRequestEntity)

    suspend fun removeById(toxId: String)

}
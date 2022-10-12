package ru.fredboy.toxoid.clean.data.source.tox

import ru.fredboy.toxoid.clean.data.model.room.FriendRequestEntity
import ru.fredboy.toxoid.clean.data.source.room.MainDatabase
import javax.inject.Inject

class RoomCachedFriendRequestDataSource @Inject constructor(
    private val mainDatabase: MainDatabase
) : CachedFriendRequestDataSource {

    override suspend fun getAll(): List<FriendRequestEntity> {
        return mainDatabase.friendRequestDao.getAll()
    }

    override suspend fun getById(toxId: String): FriendRequestEntity? {
        return mainDatabase.friendRequestDao.getByToxid(toxId)
    }

    override suspend fun add(friendRequestEntity: FriendRequestEntity) {
        mainDatabase.friendRequestDao.insert(friendRequestEntity)
    }

    override suspend fun removeById(toxId: String) {
        mainDatabase.friendRequestDao.delete(toxId)
    }

}
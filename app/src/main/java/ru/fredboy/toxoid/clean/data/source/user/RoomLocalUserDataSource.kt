package ru.fredboy.toxoid.clean.data.source.user

import ru.fredboy.toxoid.clean.data.model.room.LocalUserEntity
import ru.fredboy.toxoid.clean.data.source.room.MainDatabase
import javax.inject.Inject

class RoomLocalUserDataSource @Inject constructor(
    private val mainDatabase: MainDatabase
) : LocalUserDataSource {

    override suspend fun getAll(): List<LocalUserEntity> {
        return mainDatabase.localUserDao.getAll()
    }

    override suspend fun getById(userId: String): LocalUserEntity? {
        return mainDatabase.localUserDao.getById(userId)
    }

    override suspend fun add(user: LocalUserEntity) {
        mainDatabase.localUserDao.insert(user)
    }
}
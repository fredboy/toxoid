package ru.fredboy.toxoid.clean.data.source.bootstrap

import ru.fredboy.toxoid.clean.data.model.room.BootstrapNodeEntity
import ru.fredboy.toxoid.clean.data.source.room.MainDatabase
import javax.inject.Inject

class RoomBootstrapNodeDataSource @Inject constructor(
    private val mainDatabase: MainDatabase,
) : BootstrapNodeDataSource {

    override suspend fun getAll(): List<BootstrapNodeEntity> {
        return mainDatabase.bootstrapNodeDao.getAll()
    }

    override suspend fun add(bootstrapNodeEntity: BootstrapNodeEntity) {
        mainDatabase.bootstrapNodeDao.insert(bootstrapNodeEntity)
    }

    override suspend fun delete(publicKey: String) {
        mainDatabase.bootstrapNodeDao.delete(publicKey)
    }

}
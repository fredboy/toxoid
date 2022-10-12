package ru.fredboy.toxoid.clean.data.source.bootstrap

import ru.fredboy.toxoid.clean.data.model.room.BootstrapNodeEntity

interface BootstrapNodeDataSource {

    suspend fun getAll(): List<BootstrapNodeEntity>

    suspend fun add(bootstrapNodeEntity: BootstrapNodeEntity)

    suspend fun delete(publicKey: String)

}
package ru.fredboy.toxoid.clean.data.source.user

import ru.fredboy.toxoid.clean.data.model.room.LocalUserEntity

interface LocalUserDataSource {
    suspend fun getAll(): List<LocalUserEntity>

    suspend fun getById(userId: String): LocalUserEntity?

    suspend fun add(user: LocalUserEntity)
}
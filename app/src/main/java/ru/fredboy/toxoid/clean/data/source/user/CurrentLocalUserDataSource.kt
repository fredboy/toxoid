package ru.fredboy.toxoid.clean.data.source.user

interface CurrentLocalUserDataSource {

    suspend fun getCurrentLocalUserId(): String?

    suspend fun setCurrentLocalUserId(userId: String)

}
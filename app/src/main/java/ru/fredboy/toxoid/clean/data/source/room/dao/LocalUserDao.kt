package ru.fredboy.toxoid.clean.data.source.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.fredboy.toxoid.clean.data.model.room.LocalUserEntity
import ru.fredboy.toxoid.clean.data.source.room.MainDatabase

@Dao
interface LocalUserDao {
    @Query("SELECT * FROM ${MainDatabase.LOCAL_USER_TABLE}")
    suspend fun getAll(): List<LocalUserEntity>

    @Query("SELECT * FROM ${MainDatabase.LOCAL_USER_TABLE} WHERE id = :userId")
    suspend fun getById(userId: String): LocalUserEntity?

    @Query("SELECT * FROM ${MainDatabase.LOCAL_USER_TABLE} WHERE id in (:userIds)")
    suspend fun getByIds(userIds: Set<String>): List<LocalUserEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: LocalUserEntity)
}
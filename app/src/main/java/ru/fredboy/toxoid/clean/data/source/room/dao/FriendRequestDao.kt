package ru.fredboy.toxoid.clean.data.source.room.dao

import androidx.room.*
import ru.fredboy.toxoid.clean.data.model.room.FriendRequestEntity
import ru.fredboy.toxoid.clean.data.source.room.MainDatabase

@Dao
interface FriendRequestDao {

    @Query("SELECT * FROM ${MainDatabase.FRIEND_REQUEST_TABLE}")
    suspend fun getAll(): List<FriendRequestEntity>

    @Query("SELECT * FROM ${MainDatabase.FRIEND_REQUEST_TABLE} WHERE id = :toxid")
    suspend fun getByToxid(toxid: String): FriendRequestEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg requests: FriendRequestEntity)

    @Query("DELETE FROM ${MainDatabase.FRIEND_REQUEST_TABLE} WHERE id in (:toxIds)")
    suspend fun delete(vararg toxIds: String)

}
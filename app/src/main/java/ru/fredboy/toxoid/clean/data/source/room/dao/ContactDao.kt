package ru.fredboy.toxoid.clean.data.source.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.fredboy.toxoid.clean.data.model.room.ContactEntity
import ru.fredboy.toxoid.clean.data.source.room.MainDatabase

@Dao
interface ContactDao {

    @Query("SELECT * FROM ${MainDatabase.CONTACT_TABLE}")
    suspend fun getAll(): List<ContactEntity>

    @Query("SELECT * FROM ${MainDatabase.CONTACT_TABLE} WHERE id = :peerId")
    suspend fun getById(peerId: String): ContactEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg contacts: ContactEntity)

}
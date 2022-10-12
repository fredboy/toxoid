package ru.fredboy.toxoid.clean.data.source.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.fredboy.toxoid.clean.data.model.room.BootstrapNodeEntity
import ru.fredboy.toxoid.clean.data.source.room.MainDatabase

@Dao
interface BootstrapNodeDao {

    @Query("SELECT * FROM ${MainDatabase.BOOTSTRAP_NODES_TABLE}")
    suspend fun getAll(): List<BootstrapNodeEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg entities: BootstrapNodeEntity)

    @Query("DELETE FROM ${MainDatabase.BOOTSTRAP_NODES_TABLE} WHERE id in (:keys)")
    suspend fun delete(vararg keys: String)

}
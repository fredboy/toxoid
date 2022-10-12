package ru.fredboy.toxoid.clean.data.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.fredboy.toxoid.clean.data.source.room.MainDatabase

@Entity(tableName = MainDatabase.BOOTSTRAP_NODES_TABLE)
data class BootstrapNodeEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "host") val host: String,
    @ColumnInfo(name = "port") val port: Int,
    @ColumnInfo(name = "location") val location: String,
)
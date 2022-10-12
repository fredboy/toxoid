package ru.fredboy.toxoid.clean.data.model.room

import androidx.room.*
import ru.fredboy.toxoid.clean.data.source.room.MainDatabase

@Entity(tableName = MainDatabase.CONTACT_TABLE)
data class ContactEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String
)
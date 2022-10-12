package ru.fredboy.toxoid.clean.data.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.fredboy.toxoid.clean.data.source.room.MainDatabase

@Entity(tableName = MainDatabase.LOCAL_USER_TABLE)
data class LocalUserEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String
) {
    fun toContactEntity(): ContactEntity {
        return ContactEntity(id, name)
    }
}
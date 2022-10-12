package ru.fredboy.toxoid.clean.data.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.fredboy.toxoid.clean.data.source.room.MainDatabase

@Entity(tableName = MainDatabase.FRIEND_REQUEST_TABLE)
data class FriendRequestEntity(
    @PrimaryKey @ColumnInfo(name = "id") val toxid: String,
    @ColumnInfo(name = "message") val message: String,
)
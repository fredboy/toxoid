package ru.fredboy.toxoid.clean.data.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.fredboy.toxoid.clean.data.source.room.MainDatabase

@Entity(
    tableName = MainDatabase.CHAT_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = ContactEntity::class,
            parentColumns = ["id"],
            childColumns = ["peer_id"]
        )
    ]
)
data class ChatEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "peer_id") val peerId: String
)

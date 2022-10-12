package ru.fredboy.toxoid.clean.data.source.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.fredboy.toxoid.clean.data.model.room.ChatEntity
import ru.fredboy.toxoid.clean.data.source.room.MainDatabase

@Dao
interface ChatDao {

    @Query("SELECT * FROM ${MainDatabase.CHAT_TABLE}")
    suspend fun getAll(): List<ChatEntity>

    @Query("SELECT * FROM ${MainDatabase.CHAT_TABLE} WHERE peer_id = :peerId")
    suspend fun getChatWithPeer(peerId: String): ChatEntity?

    @Query("SELECT * FROM ${MainDatabase.CHAT_TABLE} WHERE id = :chatId")
    suspend fun getChatById(chatId: String): ChatEntity?

    @Insert
    suspend fun insert(vararg chats: ChatEntity)

}
package ru.fredboy.toxoid.clean.data.source.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.fredboy.toxoid.clean.data.model.room.MessageEntity
import ru.fredboy.toxoid.clean.data.source.room.MainDatabase

@Dao
interface MessageDao {

    @Query("SELECT * FROM ${MainDatabase.MESSAGE_TABLE} ORDER BY timestamp ASC")
    suspend fun getAll(): List<MessageEntity>

    @Query("SELECT * FROM ${MainDatabase.MESSAGE_TABLE} WHERE chat_id = :chatId ORDER BY timestamp ASC")
    suspend fun getAllFromChat(chatId: String): List<MessageEntity>

    @Query("SELECT id, sender_id, chat_id, text, MAX(timestamp) AS timestamp FROM ${MainDatabase.MESSAGE_TABLE} WHERE chat_id = :chatId")
    suspend fun getLastFromChat(chatId: String): MessageEntity?

    @Query("SELECT * FROM ${MainDatabase.MESSAGE_TABLE} WHERE sender_id = :peerId")
    suspend fun getFromPeer(peerId: String): List<MessageEntity>

    @Insert
    suspend fun insert(vararg messages: MessageEntity)

}
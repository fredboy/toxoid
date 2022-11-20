package ru.fredboy.toxoid.clean.data.source.chat

import ru.fredboy.toxoid.clean.data.model.room.ChatEntity
import ru.fredboy.toxoid.clean.data.source.room.MainDatabase
import javax.inject.Inject

class RoomChatDataSource @Inject constructor(
    private val mainDatabase: MainDatabase
) : ChatDataSource {

    override suspend fun getById(chatId: String): ChatEntity? {
        return mainDatabase.chatDao.getChatById(chatId)
    }

    override suspend fun getAll(): List<ChatEntity> {
        return mainDatabase.chatDao.getAll()
    }

    override suspend fun getAllForUser(userId: String): List<ChatEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getForContact(contactId: String): ChatEntity? {
        return mainDatabase.chatDao.getChatWithPeer(contactId)
    }

    override suspend fun add(chat: ChatEntity) {
        mainDatabase.chatDao.insert(chat)
    }
}
package ru.fredboy.toxoid.clean.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.fredboy.toxoid.clean.data.mapper.MessageMapper
import ru.fredboy.toxoid.clean.data.source.message.MessageDataSource
import ru.fredboy.toxoid.clean.domain.model.Message
import ru.fredboy.toxoid.utils.generateRandomStringId
import ru.fredboy.toxoid.utils.withIoDispatcher
import java.util.*
import javax.inject.Inject

class MessagesRepository @Inject constructor(
    private val messageDataSource: MessageDataSource,
    private val messageMapper: MessageMapper,
    private val localUsersRepository: LocalUsersRepository
) {

    suspend fun getAll(): List<Message> {
        return withIoDispatcher {
            messageDataSource.getAll().map(messageMapper::map)
        }
    }

    suspend fun getAllFromChat(chatId: String): List<Message> {
        return withIoDispatcher {
            messageDataSource.getAllFromChat(chatId).map(messageMapper::map)
        }
    }

    suspend fun add(message: Message) {
        withIoDispatcher {
            val entity = messageMapper.map(message)
            messageDataSource.add(entity)
        }
    }

    suspend fun send(chatId: String, text: String) {
        return withIoDispatcher {
            val currentUser = localUsersRepository.getCurrent() ?: return@withIoDispatcher

            val message = Message(
                id = generateRandomStringId(),
                chatId = chatId,
                senderId = currentUser.id,
                text = text,
                date = Date()
            )

            messageDataSource.sendMessage(messageMapper.map(message))
        }
    }

    fun getNewMessageFlow(): Flow<Message> {
        return messageDataSource.getNewMessageFlow()
            .flowOn(Dispatchers.IO)
            .map { dto -> messageMapper.map(dto) }
    }

}
package ru.fredboy.toxoid.clean.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import ru.fredboy.toxoid.clean.data.mapper.MessageMapper
import ru.fredboy.toxoid.clean.data.mapper.ToxPublicKeyMapper
import ru.fredboy.toxoid.clean.data.model.tox.IncomingMessageData
import ru.fredboy.toxoid.clean.data.source.chat.ChatDataSource
import ru.fredboy.toxoid.clean.data.source.intent.ToxServiceIntentApi
import ru.fredboy.toxoid.clean.data.source.message.MessageDataSource
import ru.fredboy.toxoid.clean.data.source.tox.ToxEventDataSource
import ru.fredboy.toxoid.clean.domain.model.Chat
import ru.fredboy.toxoid.clean.domain.model.LocalUser
import ru.fredboy.toxoid.clean.domain.model.Message
import ru.fredboy.toxoid.utils.generateRandomStringId
import ru.fredboy.toxoid.utils.withIoDispatcher
import java.util.*
import javax.inject.Inject

class MessagesRepository @Inject constructor(
    private val messageDataSource: MessageDataSource,
    private val chatDataSource: ChatDataSource,
    private val messageMapper: MessageMapper,
    private val toxServiceIntentApi: ToxServiceIntentApi,
    private val toxEventDataSource: ToxEventDataSource,
    private val toxPublicKeyMapper: ToxPublicKeyMapper,
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

    suspend fun send(chat: Chat, text: String, currentUser: LocalUser): Message {
        return withIoDispatcher {
            val message = Message(
                id = generateRandomStringId(),
                chatId = chat.id,
                senderId = currentUser.id,
                text = text,
                date = Date()
            )

            val recipientPublicKey = toxPublicKeyMapper.map(chat.peer.id)

            messageDataSource.sendMessage(messageMapper.map(message))

            try {
                toxServiceIntentApi.sendMessage(recipientPublicKey, message)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            message
        }
    }

    fun getNewMessageFlow(): Flow<Message> {
        return messageDataSource.getNewMessageFlow()
            .flowOn(Dispatchers.IO)
            .map { dto -> messageMapper.map(dto) }
    }

    fun flowIncomingMessage(incomingMessageData: IncomingMessageData) {
        toxEventDataSource.flowIncomingMessage(incomingMessageData)
    }

    fun getIncomingMessageFlow(): Flow<Message> {
        return toxEventDataSource.getIncominMessageFlow()
            .flowOn(Dispatchers.IO)
            .mapNotNull { data ->
                val friendPublicKey = toxServiceIntentApi.resolveFriendNumber(data.friendNumber)
                val chat = chatDataSource.getForContact(friendPublicKey.toString()) ?: return@mapNotNull null
                val message = Message(
                    id = generateRandomStringId(),
                    chatId = chat.id,
                    senderId = chat.peerId,
                    text = String(data.messageBytes),
                    date = Date(),
                )

                message
            }
    }

}
package ru.fredboy.toxoid.clean.presentation.view.chat

import kotlinx.coroutines.flow.Flow
import ru.fredboy.toxoid.clean.domain.model.Message
import ru.fredboy.toxoid.clean.domain.usecase.message.GetChatMessagesUseCase
import ru.fredboy.toxoid.clean.domain.usecase.message.GetIncomingMessageFlowUseCase
import ru.fredboy.toxoid.clean.domain.usecase.message.GetNewMessageFlowUseCase
import ru.fredboy.toxoid.clean.domain.usecase.message.SendMessageUseCase
import javax.inject.Inject

class ChatUseCases @Inject constructor(
    private val getChatMessagesUseCase: GetChatMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val getNewMessageFlowUseCase: GetNewMessageFlowUseCase,
    private val getIncomingMessageFlowUseCase: GetIncomingMessageFlowUseCase,
) {

    suspend fun getChatMessages(chatId: String): List<Message> {
        return getChatMessagesUseCase.execute(chatId)
    }

    suspend fun sendMessage(chatId: String, text: String) {
        return sendMessageUseCase.execute(chatId, text)
    }

    fun getNewMessageFlow(): Flow<Message> {
        return getNewMessageFlowUseCase.execute()
    }

    fun getIncomingMessageFlow(): Flow<Message> {
        return getIncomingMessageFlowUseCase.execute()
    }

}
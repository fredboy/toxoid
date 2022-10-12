package ru.fredboy.toxoid.clean.domain.usecase

import ru.fredboy.toxoid.clean.data.repository.MessagesRepository
import ru.fredboy.toxoid.clean.domain.model.Message
import javax.inject.Inject

class GetChatMessagesUseCase @Inject constructor(
    private val messagesRepository: MessagesRepository
) {

    suspend fun execute(chatId: String): List<Message> {
        return messagesRepository.getAllFromChat(chatId)
    }

}
package ru.fredboy.toxoid.clean.domain.usecase.message

import ru.fredboy.toxoid.clean.data.repository.ChatsRepository
import ru.fredboy.toxoid.clean.data.repository.LocalUsersRepository
import ru.fredboy.toxoid.clean.data.repository.MessagesRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val messagesRepository: MessagesRepository,
    private val chatsRepository: ChatsRepository,
    private val localUsersRepository: LocalUsersRepository,
) {

    suspend fun execute(chatId: String, text: String) {
        val chat = chatsRepository.getById(chatId) ?: return
        val currentUser = localUsersRepository.getCurrent() ?: return
        messagesRepository.send(chat, text, currentUser)
    }

}
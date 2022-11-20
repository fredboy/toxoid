package ru.fredboy.toxoid.clean.domain.usecase.chat

import ru.fredboy.toxoid.clean.data.repository.ChatsRepository
import ru.fredboy.toxoid.clean.domain.model.Chat
import javax.inject.Inject

class GetAllChatsUseCase @Inject constructor(
    private val chatsRepository: ChatsRepository
) {

    suspend fun execute(): List<Chat> {
        return chatsRepository.getAll()
    }

}
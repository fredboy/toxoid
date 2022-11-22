package ru.fredboy.toxoid.clean.domain.usecase.message

import ru.fredboy.toxoid.clean.data.repository.MessagesRepository
import ru.fredboy.toxoid.clean.domain.model.Message
import javax.inject.Inject

class SaveMessageUseCase @Inject constructor(
    private val messagesRepository: MessagesRepository,
) {

    suspend fun execute(message: Message) {
        messagesRepository.add(message)
    }

}
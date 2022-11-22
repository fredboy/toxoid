package ru.fredboy.toxoid.clean.domain.usecase.message

import kotlinx.coroutines.flow.Flow
import ru.fredboy.toxoid.clean.data.repository.MessagesRepository
import ru.fredboy.toxoid.clean.domain.model.Message
import javax.inject.Inject

class GetIncomingMessageFlowUseCase @Inject constructor(
    private val messagesRepository: MessagesRepository,
) {

    fun execute(): Flow<Message> {
        return messagesRepository.getIncomingMessageFlow()
    }

}
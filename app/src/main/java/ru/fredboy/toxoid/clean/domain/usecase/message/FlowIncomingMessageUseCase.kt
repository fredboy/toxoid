package ru.fredboy.toxoid.clean.domain.usecase.message

import ru.fredboy.toxoid.clean.data.model.tox.IncomingMessageData
import ru.fredboy.toxoid.clean.data.repository.MessagesRepository
import javax.inject.Inject

class FlowIncomingMessageUseCase @Inject constructor(
    private val messagesRepository: MessagesRepository,
) {

    fun execute(incomingMessageData: IncomingMessageData) {
        messagesRepository.flowIncomingMessage(incomingMessageData)
    }

}
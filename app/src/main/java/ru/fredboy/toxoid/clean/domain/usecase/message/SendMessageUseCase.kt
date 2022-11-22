package ru.fredboy.toxoid.clean.domain.usecase.message

import ru.fredboy.toxoid.clean.data.mapper.ToxPublicKeyMapper
import ru.fredboy.toxoid.clean.data.repository.ChatsRepository
import ru.fredboy.toxoid.clean.data.repository.MessagesRepository
import ru.fredboy.toxoid.clean.data.source.intent.ToxServiceIntentApi
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val messagesRepository: MessagesRepository,
    private val chatsRepository: ChatsRepository,
    private val toxServiceIntentApi: ToxServiceIntentApi,
    private val toxPublicKeyMapper: ToxPublicKeyMapper,
) {

    suspend fun execute(chatId: String, text: String) {
        val message = messagesRepository.send(chatId, text) ?: return
        val chat = chatsRepository.getById(chatId) ?: return
        val toxPublicKey = toxPublicKeyMapper.map(chat.peer.id)
        toxServiceIntentApi.sendMessage(toxPublicKey, message)
    }

}
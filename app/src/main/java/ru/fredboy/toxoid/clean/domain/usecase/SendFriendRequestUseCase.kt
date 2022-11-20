package ru.fredboy.toxoid.clean.domain.usecase

import ru.fredboy.toxoid.clean.data.repository.ChatsRepository
import ru.fredboy.toxoid.clean.data.repository.ContactsRepository
import ru.fredboy.toxoid.clean.data.repository.FriendRequestRepository
import ru.fredboy.toxoid.clean.data.repository.MessagesRepository
import javax.inject.Inject

class SendFriendRequestUseCase @Inject constructor(
    private val friendRequestRepository: FriendRequestRepository,
    private val contactsRepository: ContactsRepository,
    private val messagesRepository: MessagesRepository,
    private val chatsRepository: ChatsRepository,
) {

    suspend fun execute(toxId: String, message: String) {
        val contact = contactsRepository.createForToxId(toxId)
        val chat = chatsRepository.createForContact(contact)
        messagesRepository.send(chat.id, message)
        friendRequestRepository.add(toxId, message)
    }

}
package ru.fredboy.toxoid.clean.domain.usecase.friendrequest

import ru.fredboy.toxoid.clean.data.repository.*
import javax.inject.Inject

class SendFriendRequestUseCase @Inject constructor(
    private val friendRequestRepository: FriendRequestRepository,
    private val contactsRepository: ContactsRepository,
    private val messagesRepository: MessagesRepository,
    private val chatsRepository: ChatsRepository,
    private val toxOptionsRepository: ToxOptionsRepository,
    private val localUsersRepository: LocalUsersRepository,
) {

    suspend fun execute(toxId: String, message: String) {
        val currentUser = localUsersRepository.getCurrent() ?: return
        val contact = contactsRepository.createForToxId(toxId)
        val chat = chatsRepository.createForContact(contact)
        messagesRepository.send(chat, message, currentUser)
        val savedata = friendRequestRepository.add(toxId, message)
        toxOptionsRepository.saveToxData(savedata)
    }

}
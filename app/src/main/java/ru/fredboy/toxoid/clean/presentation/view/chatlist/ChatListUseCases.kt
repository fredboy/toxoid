package ru.fredboy.toxoid.clean.presentation.view.chatlist

import kotlinx.coroutines.flow.Flow
import ru.fredboy.toxoid.clean.domain.model.*
import ru.fredboy.toxoid.clean.domain.usecase.chat.GetAllChatsUseCase
import ru.fredboy.toxoid.clean.domain.usecase.chat.GetChatByContactIdUseCase
import ru.fredboy.toxoid.clean.domain.usecase.chat.GetChatByIdUseCase
import ru.fredboy.toxoid.clean.domain.usecase.contact.GetContactUpdatesFlowUseCase
import ru.fredboy.toxoid.clean.domain.usecase.friendrequest.GetNewFriendRequestFlowUseCase
import ru.fredboy.toxoid.clean.domain.usecase.message.GetNewMessageFlowUseCase
import ru.fredboy.toxoid.clean.domain.usecase.tox.CreateIdenticonUseCase
import ru.fredboy.toxoid.clean.domain.usecase.user.GetCurrentUserUseCase
import javax.inject.Inject

class ChatListUseCases @Inject constructor(
    private val getAllChatsUseCase: GetAllChatsUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getNewMessageFlowUseCase: GetNewMessageFlowUseCase,
    private val getChatByIdUseCase: GetChatByIdUseCase,
    private val getNewFriendRequestFlowUseCase: GetNewFriendRequestFlowUseCase,
    private val getContactUpdatesFlowUseCase: GetContactUpdatesFlowUseCase,
    private val getChatByContactIdUseCase: GetChatByContactIdUseCase,
    private val createIdenticonUseCase: CreateIdenticonUseCase,
) {

    suspend fun getAllChats(): List<Chat> {
        return getAllChatsUseCase.execute()
    }

    suspend fun getCurrentUser(): LocalUser? {
        return getCurrentUserUseCase.execute()
    }

    suspend fun getChatById(chatId: String): Chat? {
        return getChatByIdUseCase.execute(chatId)
    }

    fun getNewMessageFlow(): Flow<Message> {
        return getNewMessageFlowUseCase.execute()
    }

    fun getNewFriendRequestFlow(): Flow<FriendRequest> {
        return getNewFriendRequestFlowUseCase.execute()
    }

    fun getContactUpdatesFlow(): Flow<Contact> {
        return getContactUpdatesFlowUseCase.execute()
    }

    suspend fun getChatByContactId(contactId: String): Chat? {
        return getChatByContactIdUseCase.execute(contactId)
    }

    fun createIdenticon(toxId: String): Identicon {
        return createIdenticonUseCase.execute(toxId)
    }

}
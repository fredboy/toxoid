package ru.fredboy.toxoid.clean.presentation.view.chatlist

import kotlinx.coroutines.flow.Flow
import ru.fredboy.toxoid.clean.domain.model.Chat
import ru.fredboy.toxoid.clean.domain.model.FriendRequest
import ru.fredboy.toxoid.clean.domain.model.LocalUser
import ru.fredboy.toxoid.clean.domain.model.Message
import ru.fredboy.toxoid.clean.domain.usecase.*
import javax.inject.Inject

class ChatListUseCases @Inject constructor(
    private val getAllChatsUseCase: GetAllChatsUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getNewMessageFlowUseCase: GetNewMessageFlowUseCase,
    private val getChatByIdUseCase: GetChatByIdUseCase,
    private val getNewFriendRequestFlowUseCase: GetNewFriendRequestFlowUseCase,
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

}
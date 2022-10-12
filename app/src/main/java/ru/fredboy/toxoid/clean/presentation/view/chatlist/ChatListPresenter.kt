package ru.fredboy.toxoid.clean.presentation.view.chatlist

import android.util.Log
import kotlinx.coroutines.launch
import moxy.presenterScope
import ru.fredboy.toxoid.clean.domain.model.Message
import ru.fredboy.toxoid.clean.presentation.formatter.ChatListItemFormatter
import ru.fredboy.toxoid.clean.presentation.view.base.BaseMvpPresenter
import javax.inject.Inject

class ChatListPresenter @Inject constructor(
    private val useCases: ChatListUseCases,
    private val chatListItemFormatter: ChatListItemFormatter
) : BaseMvpPresenter<ChatListView>() {

    override fun onFirstViewAttach() {
        presenterScope.launch { loadAndDisplayData() }
        useCases.getNewMessageFlow()
            .schedule(::handleNewMessage)
        useCases.getNewFriendRequestFlow()
            .schedule({ request ->
                viewState.showToast("New friend request from: ${request.publicKey.toHexString()}\nMessage: ${request.message}")
            })
    }

    private fun handleNewMessage(message: Message) {
        presenterScope.launch {
            val chat = useCases.getChatById(message.chatId)
                ?.let { chatListItemFormatter.format(it) } ?: return@launch
            viewState.insertItem(chat)
        }
    }

    private suspend fun loadAndDisplayData() {
        val chats = useCases.getAllChats()
            .map(chatListItemFormatter::format)

        viewState.addChatItems(chats)
    }

    fun onListItemClick(chatId: String) {
        presenterScope.launch {
            val currentUser = useCases.getCurrentUser()
            if (currentUser != null) {
                viewState.navigateToChat(chatId, currentUser.id)
            }
        }
    }
}
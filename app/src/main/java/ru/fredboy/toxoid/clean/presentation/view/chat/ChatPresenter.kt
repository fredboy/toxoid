package ru.fredboy.toxoid.clean.presentation.view.chat

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import moxy.presenterScope
import ru.fredboy.toxoid.clean.presentation.formatter.MessageFormatter
import ru.fredboy.toxoid.clean.presentation.model.MessageVo
import ru.fredboy.toxoid.clean.presentation.view.base.BaseMvpPresenter
import javax.inject.Inject

class ChatPresenter(
    private val args: ChatFragmentArgs,
    private val useCases: ChatUseCases,
    private val messageFormatter: MessageFormatter
) : BaseMvpPresenter<ChatView>() {

    override fun onFirstViewAttach() {
        presenterScope.launch {
            useCases.getNewMessageFlow()
                .filter { it.chatId == args.chatId }
                .map { messageFormatter.format(it, args.localUserId == it.senderId) }
                .collect {
                    onNewMessage(it)
                }
        }

        loadAndDisplayData()
    }

    private fun loadAndDisplayData() {
        presenterScope.launch {
            useCases.getChatMessages(args.chatId)
                .map { messageFormatter.format(it, args.localUserId == it.senderId) }
                .also { messages ->
                    viewState.addMessages(messages)
                    viewState.scrollToBottom()
                }
        }
    }

    private fun onNewMessage(message: MessageVo) {
        viewState.addMessages(listOf(message))
        viewState.scrollToBottom()
    }

    fun onSendButtonClicked(text: String) {
        if (text.isNotBlank()) {
            presenterScope.launch {
                useCases.sendMessage(args.chatId, text.trim())
            }
        }
    }

    class Factory @Inject constructor(
        private val useCases: ChatUseCases,
        private val messageFormatter: MessageFormatter
    ) {
        fun get(args: ChatFragmentArgs): ChatPresenter {
            return ChatPresenter(args, useCases, messageFormatter)
        }
    }

}
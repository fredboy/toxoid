package ru.fredboy.toxoid.clean.presentation.view.chatlist

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.fredboy.toxoid.clean.presentation.model.ChatListItemVo

interface ChatListView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showToast(text: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun addChatItems(items: List<ChatListItemVo>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun navigateToChat(chatId: String, localUserId: String)

    @StateStrategyType(AddToEndStrategy::class)
    fun insertItem(chatListItemVo: ChatListItemVo)

}
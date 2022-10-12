package ru.fredboy.toxoid.clean.presentation.view.chat

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.fredboy.toxoid.clean.presentation.model.MessageVo

interface ChatView : MvpView {

    @StateStrategyType(AddToEndStrategy::class)
    fun addMessages(messages: List<MessageVo>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun scrollToBottom()

}
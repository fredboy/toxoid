package ru.fredboy.toxoid.clean.presentation.view.welcome

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface WelcomeView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun finishActivity()

}
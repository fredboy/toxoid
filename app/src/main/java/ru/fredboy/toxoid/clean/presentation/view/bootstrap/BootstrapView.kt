package ru.fredboy.toxoid.clean.presentation.view.bootstrap

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.fredboy.toxoid.clean.domain.model.BootstrapNode

interface BootstrapView : MvpView {

    @StateStrategyType(AddToEndStrategy::class)
    fun setNodes(nodes: List<BootstrapNode>)

}
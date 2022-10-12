package ru.fredboy.toxoid.clean.presentation.view.bootstrap

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.fredboy.toxoid.clean.presentation.model.BootstrapNodeVo

interface BootstrapView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setNodes(nodes: List<BootstrapNodeVo>)

}
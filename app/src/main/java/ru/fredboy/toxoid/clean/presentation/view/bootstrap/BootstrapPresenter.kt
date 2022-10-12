package ru.fredboy.toxoid.clean.presentation.view.bootstrap

import kotlinx.coroutines.launch
import moxy.presenterScope
import ru.fredboy.toxoid.clean.domain.model.BootstrapNode
import ru.fredboy.toxoid.clean.presentation.formatter.BootstrapNodeFormatter
import ru.fredboy.toxoid.clean.presentation.view.base.BaseMvpPresenter
import javax.inject.Inject

class BootstrapPresenter @Inject constructor(
    private val useCases: BootstrapUseCases,
    private val bootstrapNodeFormatter: BootstrapNodeFormatter,
) : BaseMvpPresenter<BootstrapView>() {

    private lateinit var nodeSelectionHandler: NodeSelectionHandler

    override fun onFirstViewAttach() {
        presenterScope.launch {
            useCases.getBootstrapNodes()
                .also { nodes -> nodeSelectionHandler = NodeSelectionHandler(nodes) }
                .map(bootstrapNodeFormatter::format)
                .let(viewState::setNodes)
        }
    }

    fun onNodeSwitched(index: Int, selected: Boolean) {
        nodeSelectionHandler.handleNodeSelection(index, selected)
    }

    private inner class NodeSelectionHandler(
        private val bootstrapNodes: List<BootstrapNode>,
    ) {

        fun handleNodeSelection(index: Int, selected: Boolean) {
            presenterScope.launch {
                if (selected) {
                    useCases.saveBootstrapNode(bootstrapNodes[index])
                } else {
                    useCases.deleteBootstrapNode(bootstrapNodes[index])
                }
            }
        }

    }

}
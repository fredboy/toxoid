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

    fun onNodesSelected(selectedIndices: List<Int>) {
        nodeSelectionHandler.handleNodeSelection(selectedIndices)
    }

    private inner class NodeSelectionHandler(
        private val bootstrapNodes: List<BootstrapNode>,
    ) {

        fun handleNodeSelection(selectedIndices: List<Int>) {
            presenterScope.launch {
                useCases.saveBootstrapNodes(bootstrapNodes.slice(selectedIndices))
            }
        }

    }

}
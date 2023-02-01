package ru.fredboy.toxoid.clean.presentation.view.bootstrap

import ru.fredboy.toxoid.clean.domain.model.BootstrapNode
import ru.fredboy.toxoid.clean.domain.usecase.bootstrap.DeleteBootstrapNodeUseCase
import ru.fredboy.toxoid.clean.domain.usecase.bootstrap.GetBootstrapNodesUseCase
import ru.fredboy.toxoid.clean.domain.usecase.bootstrap.SaveBootstrapNodesUseCase
import javax.inject.Inject

class BootstrapUseCases @Inject constructor(
    private val getBootstrapNodesUseCase: GetBootstrapNodesUseCase,
    private val saveBootstrapNodesUseCase: SaveBootstrapNodesUseCase,
    private val deleteBootstrapNodeUseCase: DeleteBootstrapNodeUseCase,
) {

    suspend fun getBootstrapNodes(): List<BootstrapNode> {
        return getBootstrapNodesUseCase.execute()
    }

    suspend fun saveBootstrapNodes(bootstrapNodes: List<BootstrapNode>) {
        saveBootstrapNodesUseCase.execute(bootstrapNodes)
    }

    suspend fun deleteBootstrapNode(bootstrapNode: BootstrapNode) {
        deleteBootstrapNodeUseCase.execute(bootstrapNode)
    }

}
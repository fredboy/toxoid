package ru.fredboy.toxoid.clean.presentation.view.bootstrap

import ru.fredboy.toxoid.clean.domain.model.BootstrapNode
import ru.fredboy.toxoid.clean.domain.usecase.bootstrap.DeleteBootstrapNodeUseCase
import ru.fredboy.toxoid.clean.domain.usecase.bootstrap.GetBootstrapNodesUseCase
import ru.fredboy.toxoid.clean.domain.usecase.bootstrap.SaveBootstrapNodeUseCase
import javax.inject.Inject

class BootstrapUseCases @Inject constructor(
    private val getBootstrapNodesUseCase: GetBootstrapNodesUseCase,
    private val saveBootstrapNodeUseCase: SaveBootstrapNodeUseCase,
    private val deleteBootstrapNodeUseCase: DeleteBootstrapNodeUseCase,
) {

    suspend fun getBootstrapNodes(): List<BootstrapNode> {
        return getBootstrapNodesUseCase.execute()
    }

    suspend fun saveBootstrapNode(bootstrapNode: BootstrapNode) {
        saveBootstrapNodeUseCase.execute(bootstrapNode)
    }

    suspend fun deleteBootstrapNode(bootstrapNode: BootstrapNode) {
        deleteBootstrapNodeUseCase.execute(bootstrapNode)
    }

}
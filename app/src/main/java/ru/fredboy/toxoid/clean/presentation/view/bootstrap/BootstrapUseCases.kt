package ru.fredboy.toxoid.clean.presentation.view.bootstrap

import ru.fredboy.toxoid.clean.domain.model.BootstrapNode
import ru.fredboy.toxoid.clean.domain.usecase.GetBootstrapNodesUseCase
import javax.inject.Inject

class BootstrapUseCases @Inject constructor(
    private val getBootstrapNodesUseCase: GetBootstrapNodesUseCase
) {

    suspend fun getBootstrapNodes(): List<BootstrapNode> {
        return getBootstrapNodesUseCase.execute()
    }

}
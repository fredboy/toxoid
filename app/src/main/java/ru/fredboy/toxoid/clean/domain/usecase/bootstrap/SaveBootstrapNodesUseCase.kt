package ru.fredboy.toxoid.clean.domain.usecase.bootstrap

import ru.fredboy.toxoid.clean.data.repository.BootstrapNodesRepository
import ru.fredboy.toxoid.clean.domain.model.BootstrapNode
import javax.inject.Inject

class SaveBootstrapNodesUseCase @Inject constructor(
    private val bootstrapNodesRepository: BootstrapNodesRepository
) {

    suspend fun execute(bootstrapNodes: List<BootstrapNode>) {
        bootstrapNodesRepository.saveNodes(bootstrapNodes)
    }

}
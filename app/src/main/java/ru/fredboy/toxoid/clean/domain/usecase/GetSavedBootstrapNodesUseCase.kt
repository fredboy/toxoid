package ru.fredboy.toxoid.clean.domain.usecase

import ru.fredboy.toxoid.clean.data.repository.BootstrapNodesRepository
import ru.fredboy.toxoid.clean.domain.model.BootstrapNode
import javax.inject.Inject

class GetSavedBootstrapNodesUseCase @Inject constructor(
    private val bootstrapNodesRepository: BootstrapNodesRepository,
) {

    suspend fun execute(): List<BootstrapNode> {
        return bootstrapNodesRepository.getAllSavedNodes()
    }

}
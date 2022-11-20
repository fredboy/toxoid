package ru.fredboy.toxoid.clean.domain.usecase.tox

import kotlinx.coroutines.flow.Flow
import ru.fredboy.toxoid.clean.data.repository.OwnToxIdRepository
import ru.fredboy.toxoid.utils.ToxId
import javax.inject.Inject

class GetOwnToxIdFlowUseCase @Inject constructor(
    private val ownToxIdRepository: OwnToxIdRepository
) {

    fun execute(): Flow<ToxId> {
        return ownToxIdRepository.getOwnToxIdFlow()
    }

}
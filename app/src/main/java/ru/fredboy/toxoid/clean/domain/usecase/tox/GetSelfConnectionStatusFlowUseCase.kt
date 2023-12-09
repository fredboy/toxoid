package ru.fredboy.toxoid.clean.domain.usecase.tox

import kotlinx.coroutines.flow.Flow
import ru.fredboy.tox4a.api.core.data.enums.ToxConnection
import ru.fredboy.toxoid.clean.data.repository.ToxSelfConnectionRepository
import javax.inject.Inject

class GetSelfConnectionStatusFlowUseCase @Inject constructor(
    private val toxSelfConnectionRepository: ToxSelfConnectionRepository
) {

    fun execute(): Flow<ToxConnection> {
        return toxSelfConnectionRepository.getSelfConnectionStatusFlow()
    }

}
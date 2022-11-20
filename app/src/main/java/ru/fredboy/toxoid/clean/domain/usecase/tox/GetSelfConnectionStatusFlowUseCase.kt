package ru.fredboy.toxoid.clean.domain.usecase.tox

import im.tox.tox4j.core.enums.ToxConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import ru.fredboy.toxoid.clean.data.repository.ToxSelfConnectionRepository
import javax.inject.Inject

class GetSelfConnectionStatusFlowUseCase @Inject constructor(
    private val toxSelfConnectionRepository: ToxSelfConnectionRepository
) {

    fun execute(): Flow<ToxConnection> {
        return toxSelfConnectionRepository.getSelfConnectionStatusFlow()
    }

}
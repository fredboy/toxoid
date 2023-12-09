package ru.fredboy.toxoid.clean.domain.usecase.tox

import ru.fredboy.tox4a.api.core.data.enums.ToxConnection
import ru.fredboy.toxoid.clean.data.repository.ToxSelfConnectionRepository
import javax.inject.Inject

class GetLatestSelfConnectionStatusUseCase @Inject constructor(
    private val toxSelfConnectionRepository: ToxSelfConnectionRepository
) {

    fun execute(): ToxConnection {
        return toxSelfConnectionRepository.getLatestSelfConnectionStatus()
    }

}
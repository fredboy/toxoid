package ru.fredboy.toxoid.clean.domain.usecase.tox

import im.tox.tox4j.core.enums.ToxConnection
import ru.fredboy.toxoid.clean.data.repository.ToxSelfConnectionRepository
import javax.inject.Inject

class GetLatestSelfConnectionStatusUseCase @Inject constructor(
    private val toxSelfConnectionRepository: ToxSelfConnectionRepository
) {

    fun execute(): ToxConnection {
        return toxSelfConnectionRepository.getLatestSelfConnectionStatus()
    }

}
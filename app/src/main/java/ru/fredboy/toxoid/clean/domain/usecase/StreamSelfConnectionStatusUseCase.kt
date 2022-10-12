package ru.fredboy.toxoid.clean.domain.usecase

import im.tox.tox4j.core.enums.ToxConnection
import ru.fredboy.toxoid.clean.data.repository.ToxSelfConnectionRepository
import javax.inject.Inject

class StreamSelfConnectionStatusUseCase @Inject constructor(
    private val toxSelfConnectionRepository: ToxSelfConnectionRepository
) {

    fun execute(connection: ToxConnection) {
        toxSelfConnectionRepository.streamSelfConnectionStatus(connection)
    }

}
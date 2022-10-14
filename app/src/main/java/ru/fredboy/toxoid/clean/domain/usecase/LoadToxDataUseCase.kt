package ru.fredboy.toxoid.clean.domain.usecase

import im.tox.tox4j.core.options.ToxOptions
import ru.fredboy.toxoid.clean.data.repository.ToxOptionsRepository
import javax.inject.Inject

class LoadToxDataUseCase @Inject constructor(
    private val toxOptionsRepository: ToxOptionsRepository,
) {

    suspend fun execute(toxId: String): ToxOptions {
        return toxOptionsRepository.loadToxData(toxId)
    }

}
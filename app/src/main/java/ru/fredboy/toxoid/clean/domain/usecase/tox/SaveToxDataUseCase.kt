package ru.fredboy.toxoid.clean.domain.usecase.tox

import ru.fredboy.toxoid.clean.data.repository.ToxOptionsRepository
import javax.inject.Inject

class SaveToxDataUseCase @Inject constructor(
    private val toxOptionsRepository: ToxOptionsRepository,
) {

    suspend fun execute(toxId: String, data: ByteArray) {
        toxOptionsRepository.saveToxData(toxId, data)
    }

}
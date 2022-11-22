package ru.fredboy.toxoid.tox.api

import ru.fredboy.toxoid.clean.domain.usecase.tox.SaveToxDataUseCase
import javax.inject.Inject

class ToxApiHandlerUseCases @Inject constructor(
    private val saveToxDataUseCase: SaveToxDataUseCase,
) {

    suspend fun saveToxData(toxId: String, data: ByteArray) {
        saveToxDataUseCase.execute(toxId, data)
    }

}
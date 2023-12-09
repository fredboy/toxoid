package ru.fredboy.toxoid.clean.domain.usecase.tox

import ru.fredboy.tox4a.api.core.options.ToxOptions
import ru.fredboy.toxoid.clean.data.repository.ToxOptionsRepository
import javax.inject.Inject

class CreateNewToxOptionsUseCase @Inject constructor(
    private val toxOptionsRepository: ToxOptionsRepository
) {

    suspend fun execute(): ToxOptions {
        return toxOptionsRepository.createNew()
    }

}
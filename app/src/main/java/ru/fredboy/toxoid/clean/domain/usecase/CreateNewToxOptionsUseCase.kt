package ru.fredboy.toxoid.clean.domain.usecase

import androidx.annotation.WorkerThread
import im.tox.tox4j.core.options.ToxOptions
import ru.fredboy.toxoid.clean.data.repository.ToxOptionsRepository
import javax.inject.Inject

class CreateNewToxOptionsUseCase @Inject constructor(
    private val toxOptionsRepository: ToxOptionsRepository
) {

    @WorkerThread
    fun execute(): ToxOptions {
        return toxOptionsRepository.createNew()
    }

}
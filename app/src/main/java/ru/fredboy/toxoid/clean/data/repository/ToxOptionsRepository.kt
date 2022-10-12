package ru.fredboy.toxoid.clean.data.repository

import androidx.annotation.WorkerThread
import im.tox.tox4j.core.options.ToxOptions
import ru.fredboy.toxoid.clean.data.source.tox.ToxOptionsDataSource
import ru.fredboy.toxoid.utils.withIoDispatcher
import javax.inject.Inject

class ToxOptionsRepository @Inject constructor(
    private val toxOptionsDataSource: ToxOptionsDataSource
) {

    @WorkerThread
    fun createNew(): ToxOptions {
        return toxOptionsDataSource.createNew()
    }

}
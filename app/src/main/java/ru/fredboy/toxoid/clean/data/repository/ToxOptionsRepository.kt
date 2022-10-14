package ru.fredboy.toxoid.clean.data.repository

import im.tox.tox4j.core.options.ToxOptions
import ru.fredboy.toxoid.clean.data.source.tox.ToxOptionsDataSource
import javax.inject.Inject

class ToxOptionsRepository @Inject constructor(
    private val toxOptionsDataSource: ToxOptionsDataSource
) {

    suspend fun createNew(): ToxOptions {
        return toxOptionsDataSource.createNew()
    }

    suspend fun saveToxData(toxId: String, data: ByteArray) {
        toxOptionsDataSource.saveToxData(toxId, data)
    }

    suspend fun loadToxData(toxId: String): ToxOptions {
        return toxOptionsDataSource.getForUser(toxId)
    }

}
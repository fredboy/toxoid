package ru.fredboy.toxoid.clean.data.source.tox

import ru.fredboy.tox4a.api.core.options.ToxOptions

interface ToxOptionsDataSource {

    suspend fun createNew(): ToxOptions

    suspend fun saveToxData(toxId: String, data: ByteArray)

    suspend fun getForUser(userId: String): ToxOptions

}
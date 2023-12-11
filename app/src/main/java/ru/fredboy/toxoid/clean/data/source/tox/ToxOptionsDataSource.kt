package ru.fredboy.toxoid.clean.data.source.tox

import im.tox.tox4j.core.options.ToxOptions

interface ToxOptionsDataSource {

    suspend fun createNew(): ToxOptions

    suspend fun saveToxData(toxId: String, data: ByteArray)

    suspend fun getForUser(userId: String): ToxOptions

}
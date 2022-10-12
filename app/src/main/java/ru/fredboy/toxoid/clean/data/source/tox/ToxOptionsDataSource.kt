package ru.fredboy.toxoid.clean.data.source.tox

import androidx.annotation.WorkerThread
import im.tox.tox4j.core.options.ToxOptions

interface ToxOptionsDataSource {

    @WorkerThread
    fun createNew(): ToxOptions

    @WorkerThread
    fun saveToxData(toxId: String, data: ByteArray)

    @WorkerThread
    fun getForUser(userId: String): ToxOptions?

}
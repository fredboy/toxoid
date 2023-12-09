package ru.fredboy.toxoid.clean.data.repository

import ru.fredboy.tox4a.api.core.options.ToxOptions
import ru.fredboy.toxoid.clean.data.model.tox.ToxSaveData
import ru.fredboy.toxoid.clean.data.source.tox.ToxOptionsDataSource
import ru.fredboy.toxoid.utils.bytesToHexString
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

    suspend fun saveToxData(toxSaveData: ToxSaveData) {
        saveToxData(bytesToHexString(toxSaveData.ownAddressBytes), toxSaveData.toxSaveData)
    }

}
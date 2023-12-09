package ru.fredboy.toxoid.clean.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import ru.fredboy.tox4a.api.core.data.enums.ToxConnection
import ru.fredboy.toxoid.clean.data.source.tox.ToxEventDataSource
import javax.inject.Inject

class ToxSelfConnectionRepository @Inject constructor(
    private val toxEventDataSource: ToxEventDataSource
) {

    fun streamSelfConnectionStatus(connection: ToxConnection) {
        toxEventDataSource.streamSefConnectionStatus(connection)
    }

    fun getSelfConnectionStatusFlow(): Flow<ToxConnection> {
        return toxEventDataSource.getSelfConnectionStatusFlow().flowOn(Dispatchers.IO)
    }

    fun getLatestSelfConnectionStatus(): ToxConnection {
        return toxEventDataSource.getLatestSelfConnectionStatus()
    }

}
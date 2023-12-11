package ru.fredboy.toxoid.clean.data.repository

import im.tox.tox4j.core.enums.ToxConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
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
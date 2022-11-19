package ru.fredboy.toxoid.clean.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import ru.fredboy.toxoid.clean.data.source.tox.ToxEventDataSource
import ru.fredboy.toxoid.utils.ToxId
import javax.inject.Inject

class OwnToxIdRepository @Inject constructor(
    private val toxEventDataSource: ToxEventDataSource,
) {

    fun getOwnToxIdFlow(): Flow<ToxId> {
        return toxEventDataSource.getOwnToxIdFlow().flowOn(Dispatchers.IO)
    }

    fun setOwnToxId(toxId: ToxId) {
        toxEventDataSource.setOwnToxId(toxId)
    }

}
package ru.fredboy.toxoid.clean.data.repository

import kotlinx.coroutines.flow.Flow
import ru.fredboy.toxoid.clean.data.source.tox.ToxEventDataSource
import ru.fredboy.toxoid.utils.ToxId
import javax.inject.Inject

class OwnToxIdRepository @Inject constructor(
    private val toxEventDataSource: ToxEventDataSource,
) {

    fun getOwnToxIdFlow(): Flow<ToxId> {
        return toxEventDataSource.getOwnToxIdFlow()
    }

    fun setOwnToxId(toxId: ToxId) {
        toxEventDataSource.setOwnToxId(toxId)
    }

}
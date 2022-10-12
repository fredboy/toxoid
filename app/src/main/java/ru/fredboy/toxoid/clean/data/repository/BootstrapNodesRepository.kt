package ru.fredboy.toxoid.clean.data.repository

import ru.fredboy.toxoid.clean.data.mapper.BootstrapNodeMapper
import ru.fredboy.toxoid.clean.data.source.bootstrap.ToxChatNodesDataSource
import ru.fredboy.toxoid.clean.domain.model.BootstrapNode
import ru.fredboy.toxoid.utils.withIoDispatcher
import javax.inject.Inject

class BootstrapNodesRepository @Inject constructor(
    private val toxChatNodesDataSource: ToxChatNodesDataSource,
    private val bootstrapNodeMapper: BootstrapNodeMapper,
) {

    suspend fun getToxChatNodes(): List<BootstrapNode> {
        return withIoDispatcher {
            toxChatNodesDataSource.getNodes()?.nodes?.mapNotNull { dto ->
                bootstrapNodeMapper.map(dto)
            } ?: emptyList()
        }
    }

}
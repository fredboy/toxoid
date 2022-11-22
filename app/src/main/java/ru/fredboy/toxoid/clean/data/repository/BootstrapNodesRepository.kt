package ru.fredboy.toxoid.clean.data.repository

import ru.fredboy.toxoid.clean.data.mapper.BootstrapNodeMapper
import ru.fredboy.toxoid.clean.data.source.bootstrap.BootstrapNodeDataSource
import ru.fredboy.toxoid.clean.data.source.bootstrap.ToxChatNodesDataSource
import ru.fredboy.toxoid.clean.domain.model.BootstrapNode
import ru.fredboy.toxoid.utils.withIoDispatcher
import javax.inject.Inject

class BootstrapNodesRepository @Inject constructor(
    private val toxChatNodesDataSource: ToxChatNodesDataSource,
    private val bootstrapNodeMapper: BootstrapNodeMapper,
    private val bootstrapNodeDataSource: BootstrapNodeDataSource,
) {

    suspend fun getAvailableNodes(): List<BootstrapNode> {
        return withIoDispatcher {
            toxChatNodesDataSource.getNodes()?.nodes?.map { dto ->
                bootstrapNodeMapper.map(dto)
            } ?: emptyList()
        }
    }

    suspend fun getAllSavedNodes(): List<BootstrapNode> {
        return withIoDispatcher {
            bootstrapNodeDataSource.getAll().map { entity ->
                bootstrapNodeMapper.map(entity)
            }
        }
    }

    suspend fun saveNode(bootstrapNode: BootstrapNode) {
        withIoDispatcher {
            bootstrapNodeDataSource.add(bootstrapNodeMapper.map(bootstrapNode))
        }
    }

    suspend fun deleteNode(bootstrapNode: BootstrapNode) {
        withIoDispatcher {
            bootstrapNodeDataSource.delete(bootstrapNode.publicKey.toString())
        }
    }

}
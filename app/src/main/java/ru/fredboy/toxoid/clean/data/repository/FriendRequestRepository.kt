package ru.fredboy.toxoid.clean.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import ru.fredboy.toxoid.clean.data.mapper.FriendRequestMapper
import ru.fredboy.toxoid.clean.data.model.FriendRequestData
import ru.fredboy.toxoid.clean.data.source.tox.CachedFriendRequestDataSource
import ru.fredboy.toxoid.clean.data.source.tox.ToxEventDataSource
import ru.fredboy.toxoid.clean.domain.model.FriendRequest
import ru.fredboy.toxoid.utils.withIoDispatcher
import javax.inject.Inject

class FriendRequestRepository @Inject constructor(
    private val cachedFriendRequestDataSource: CachedFriendRequestDataSource,
    private val friendRequestMapper: FriendRequestMapper,
    private val toxEventDataSource: ToxEventDataSource
) {

    suspend fun getAll(): List<FriendRequest> {
        return withIoDispatcher {
            cachedFriendRequestDataSource.getAll()
                .map { friendRequestMapper.map(it) }
        }
    }

    suspend fun getByToxId(toxId: String): FriendRequest? {
        return withIoDispatcher {
            cachedFriendRequestDataSource.getById(toxId)
                ?.let { friendRequestMapper.map(it) }
        }
    }

    suspend fun add(request: FriendRequest) {
        withIoDispatcher {
            val entity = friendRequestMapper.map(request)
            cachedFriendRequestDataSource.add(entity)
        }
    }

    suspend fun deleteByToxId(toxId: String) {
        withIoDispatcher {
            cachedFriendRequestDataSource.removeById(toxId)
        }
    }

    suspend fun acceptFriendRequest(toxId: String) {
        TODO("Not yet implemented")
    }

    fun getNewFriendRequestFlow(): Flow<FriendRequest> {
        return toxEventDataSource.getFriendRequestFlow()
            .map {
                val entity = friendRequestMapper.map(it)
                runBlocking { cachedFriendRequestDataSource.add(entity) }
                friendRequestMapper.map(entity)
            }
    }

    fun broadcastNewFriendRequest(requestData: FriendRequestData) {
        toxEventDataSource.newFriendRequest(requestData)
    }

}
package ru.fredboy.toxoid.clean.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ru.fredboy.toxoid.clean.data.mapper.FriendRequestMapper
import ru.fredboy.toxoid.clean.data.mapper.ToxFriendAddressMapper
import ru.fredboy.toxoid.clean.data.model.FriendRequestData
import ru.fredboy.toxoid.clean.data.source.intent.ToxServiceIntentApi
import ru.fredboy.toxoid.clean.data.source.tox.CachedFriendRequestDataSource
import ru.fredboy.toxoid.clean.data.source.tox.ToxEventDataSource
import ru.fredboy.toxoid.clean.domain.model.FriendRequest
import ru.fredboy.toxoid.utils.withIoDispatcher
import javax.inject.Inject

class FriendRequestRepository @Inject constructor(
    private val cachedFriendRequestDataSource: CachedFriendRequestDataSource,
    private val friendRequestMapper: FriendRequestMapper,
    private val toxEventDataSource: ToxEventDataSource,
    private val toxFriendAddressMapper: ToxFriendAddressMapper,
    private val toxServiceIntentApi: ToxServiceIntentApi,
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

    suspend fun add(toxId: String, message: String) {
        withIoDispatcher {
            val request = createFriendRequest(toxId, message)
            val entity = friendRequestMapper.map(request)
            cachedFriendRequestDataSource.add(entity)
            withContext(Dispatchers.Main) {
                toxServiceIntentApi.addFriend(request)
            }
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
            .flowOn(Dispatchers.IO)
            .map {
                val entity = friendRequestMapper.map(it)
                runBlocking { cachedFriendRequestDataSource.add(entity) }
                friendRequestMapper.map(entity)
            }
    }

    fun broadcastNewFriendRequest(requestData: FriendRequestData) {
        toxEventDataSource.newFriendRequest(requestData)
    }

    private fun createFriendRequest(toxId: String, message: String): FriendRequest {
        val address = toxFriendAddressMapper.map(toxId)
        return FriendRequest(
            friendAddress = address,
            message = message
        )
    }

}
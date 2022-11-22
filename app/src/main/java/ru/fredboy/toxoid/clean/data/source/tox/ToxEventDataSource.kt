package ru.fredboy.toxoid.clean.data.source.tox

import im.tox.tox4j.core.enums.ToxConnection
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.fredboy.toxoid.clean.data.model.FriendRequestData
import ru.fredboy.toxoid.clean.domain.model.ToxAddress
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToxEventDataSource @Inject constructor() {

    private val ownToxAddressFlow = MutableSharedFlow<ToxAddress>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val toxSelfConnectionStatusFlow = MutableStateFlow(ToxConnection.NONE)

    private val friendRequestFlow = MutableSharedFlow<FriendRequestData>(
        extraBufferCapacity = 10
    )

    fun newFriendRequest(requestData: FriendRequestData) {
        friendRequestFlow.tryEmit(requestData)
    }

    fun getFriendRequestFlow(): Flow<FriendRequestData> {
        return friendRequestFlow
    }

    fun setOwnToxAddress(address: ToxAddress) {
        ownToxAddressFlow.tryEmit(address)
    }

    fun getOwnToxAddressFlow(): Flow<ToxAddress> {
        return ownToxAddressFlow
    }

    fun streamSefConnectionStatus(connectionStatus: ToxConnection) {
        toxSelfConnectionStatusFlow.tryEmit(connectionStatus)
    }

    fun getSelfConnectionStatusFlow(): Flow<ToxConnection> {
        return toxSelfConnectionStatusFlow
    }

    fun getLatestSelfConnectionStatus(): ToxConnection {
        return toxSelfConnectionStatusFlow.value
    }

}
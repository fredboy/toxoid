package ru.fredboy.toxoid.clean.data.source.tox

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.fredboy.tox4a.api.core.data.enums.ToxConnection
import ru.fredboy.toxoid.clean.data.model.tox.FriendRequestData
import ru.fredboy.toxoid.clean.data.model.tox.IncomingMessageData
import ru.fredboy.toxoid.clean.data.model.tox.NewFriendNameData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToxEventDataSource @Inject constructor() {

    private val toxSelfConnectionStatusFlow = MutableStateFlow(ToxConnection.NONE)

    private val friendRequestFlow = MutableSharedFlow<FriendRequestData>(
        extraBufferCapacity = 10
    )

    private val friendNameFlow = MutableSharedFlow<NewFriendNameData>(
        extraBufferCapacity = 10
    )

    private val incomingMessageFlow = MutableSharedFlow<IncomingMessageData>(
        extraBufferCapacity = 100
    )

    fun newFriendRequest(requestData: FriendRequestData) {
        friendRequestFlow.tryEmit(requestData)
    }

    fun getFriendRequestFlow(): Flow<FriendRequestData> {
        return friendRequestFlow
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

    fun flowNewFriendNameData(friendNameData: NewFriendNameData) {
        friendNameFlow.tryEmit(friendNameData)
    }

    fun getNewFriendNameDataFlow(): Flow<NewFriendNameData> {
        return friendNameFlow
    }

    fun flowIncomingMessage(message: IncomingMessageData) {
        incomingMessageFlow.tryEmit(message)
    }

    fun getIncominMessageFlow(): Flow<IncomingMessageData> {
        return incomingMessageFlow
    }

}
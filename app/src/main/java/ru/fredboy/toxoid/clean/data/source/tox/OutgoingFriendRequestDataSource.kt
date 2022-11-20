package ru.fredboy.toxoid.clean.data.source.tox

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import ru.fredboy.toxoid.clean.domain.model.FriendRequest
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OutgoingFriendRequestDataSource @Inject constructor() {

    private val outgoingFriendRequestFlow = MutableSharedFlow<FriendRequest>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    fun send(friendRequest: FriendRequest) {
        outgoingFriendRequestFlow.tryEmit(friendRequest)
    }

    fun getFlow(): Flow<FriendRequest> {
        return outgoingFriendRequestFlow
    }

}
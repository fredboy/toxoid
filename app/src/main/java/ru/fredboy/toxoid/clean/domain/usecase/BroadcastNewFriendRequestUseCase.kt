package ru.fredboy.toxoid.clean.domain.usecase

import ru.fredboy.toxoid.clean.data.model.FriendRequestData
import ru.fredboy.toxoid.clean.data.repository.FriendRequestRepository
import javax.inject.Inject

class BroadcastNewFriendRequestUseCase @Inject constructor(
    private val friendRequestRepository: FriendRequestRepository
) {

    fun execute(requestData: FriendRequestData) {
        friendRequestRepository.broadcastNewFriendRequest(requestData)
    }

}
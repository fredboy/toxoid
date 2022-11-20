package ru.fredboy.toxoid.clean.domain.usecase.friendrequest

import ru.fredboy.toxoid.clean.data.repository.FriendRequestRepository
import javax.inject.Inject

class AcceptFriendRequestUseCase @Inject constructor(
    private val friendRequestRepository: FriendRequestRepository
) {

    suspend fun execute(toxId: String) {
        return friendRequestRepository.acceptFriendRequest(toxId)
    }

}
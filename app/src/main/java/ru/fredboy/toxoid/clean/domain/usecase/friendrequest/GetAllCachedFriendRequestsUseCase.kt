package ru.fredboy.toxoid.clean.domain.usecase.friendrequest

import ru.fredboy.toxoid.clean.data.repository.FriendRequestRepository
import ru.fredboy.toxoid.clean.domain.model.FriendRequest
import javax.inject.Inject

class GetAllCachedFriendRequestsUseCase @Inject constructor(
    private val friendRequestRepository: FriendRequestRepository
) {

    suspend fun execute(): List<FriendRequest> {
        return friendRequestRepository.getAll()
    }

}
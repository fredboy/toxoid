package ru.fredboy.toxoid.clean.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.fredboy.toxoid.clean.data.repository.FriendRequestRepository
import ru.fredboy.toxoid.clean.domain.model.FriendRequest
import javax.inject.Inject

class GetNewFriendRequestFlowUseCase @Inject constructor(
    private val friendRequestRepository: FriendRequestRepository
) {

    fun execute(): Flow<FriendRequest> {
        return friendRequestRepository.getNewFriendRequestFlow()
    }

}
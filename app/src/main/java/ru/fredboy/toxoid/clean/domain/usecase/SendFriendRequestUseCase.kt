package ru.fredboy.toxoid.clean.domain.usecase

import ru.fredboy.toxoid.clean.data.repository.FriendRequestRepository
import ru.fredboy.toxoid.clean.domain.model.FriendRequest
import javax.inject.Inject

class SendFriendRequestUseCase @Inject constructor(
    private val friendRequestRepository: FriendRequestRepository,
) {

    suspend fun execute(toxId: String, message: String) {
        friendRequestRepository.add(toxId, message)
    }

}
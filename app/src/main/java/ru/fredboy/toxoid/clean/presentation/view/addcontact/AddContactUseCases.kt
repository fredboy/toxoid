package ru.fredboy.toxoid.clean.presentation.view.addcontact

import ru.fredboy.toxoid.clean.domain.usecase.friendrequest.SendFriendRequestUseCase
import javax.inject.Inject

class AddContactUseCases @Inject constructor(
    private val sendFriendRequestUseCase: SendFriendRequestUseCase,
) {

    suspend fun sendFriendRequest(toxId: String, message: String) {
        sendFriendRequestUseCase.execute(toxId, message)
    }

}
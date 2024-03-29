package ru.fredboy.toxoid.clean.presentation.view.addcontact

import ru.fredboy.toxoid.clean.domain.model.Identicon
import ru.fredboy.toxoid.clean.domain.usecase.friendrequest.SendFriendRequestUseCase
import ru.fredboy.toxoid.clean.domain.usecase.tox.CreateIdenticonUseCase
import javax.inject.Inject

class AddContactUseCases @Inject constructor(
    private val sendFriendRequestUseCase: SendFriendRequestUseCase,
    private val createIdenticonUseCase: CreateIdenticonUseCase,
) {

    suspend fun sendFriendRequest(toxId: String, message: String) {
        sendFriendRequestUseCase.execute(toxId, message)
    }

    fun createIdenticon(toxId: String): Identicon {
        return createIdenticonUseCase.execute(toxId)
    }

}
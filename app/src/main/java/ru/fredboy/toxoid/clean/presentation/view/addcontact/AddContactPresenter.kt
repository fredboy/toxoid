package ru.fredboy.toxoid.clean.presentation.view.addcontact

import kotlinx.coroutines.launch
import moxy.presenterScope
import ru.fredboy.toxoid.clean.domain.model.FriendRequest
import ru.fredboy.toxoid.clean.presentation.view.base.BaseMvpPresenter
import javax.inject.Inject

class AddContactPresenter @Inject constructor(
    private val useCases: AddContactUseCases,
) : BaseMvpPresenter<AddContactView>() {

    fun sendFriendRequest(toxId: String, message: String = "Hello") {
        presenterScope.launch {
            useCases.sendFriendRequest(toxId, message)
        }
    }

}
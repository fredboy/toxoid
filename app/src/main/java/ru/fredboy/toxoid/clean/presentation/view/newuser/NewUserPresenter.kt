package ru.fredboy.toxoid.clean.presentation.view.newuser

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import moxy.presenterScope
import ru.fredboy.toxoid.clean.domain.model.Identicon
import ru.fredboy.toxoid.clean.domain.model.LocalUser
import ru.fredboy.toxoid.clean.presentation.view.base.BaseMvpPresenter
import javax.inject.Inject

class NewUserPresenter @Inject constructor(
    @ApplicationContext private val context: Context,
    private val useCases: NewUserUseCases
) : BaseMvpPresenter<NewUserView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        createIdenticon()
    }

    private fun createIdenticon() {
        presenterScope.launch {
            val address = useCases.getOwnToxAddress()
            val identicon = Identicon(address).getDrawable(context.resources, 160)
            viewState.showUserPhoto(identicon)
        }
    }

    fun createNewUser(name: String, password: String) {
        if (name.isBlank() || password.isEmpty()) {
            return
        }

        presenterScope.launch {
            val address = useCases.getOwnToxAddress()
            val stringId = address.toString()
            val user = LocalUser(id = stringId, name = name)
            useCases.addNewUser(user)
            useCases.setCurrentUserUseCase(stringId)
            viewState.setNewUser(user)
            useCases.setFirstLaunch()
        }
    }

    fun initToxService() {
        useCases.initToxService()
    }

}
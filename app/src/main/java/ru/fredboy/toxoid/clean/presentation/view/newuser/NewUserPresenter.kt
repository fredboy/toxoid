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
        useCases.getOwnToxIdFlow()
            .schedule({ toxId ->
                viewState.showUserPhoto(
                    Identicon(toxId)
                        .getDrawable(context.resources, 160)
                )
            })
    }

    fun createNewUser(name: String, password: String) {
        if (name.isBlank() || password.isEmpty()) {
            return
        }

        useCases.getOwnToxIdFlow()
            .schedule( { toxId ->
                val stringId = String(toxId)
                val user = LocalUser(id = stringId, name = name)
                presenterScope.launch {
                    useCases.addNewUser(user)
                    useCases.setCurrentUserUseCase(stringId)
                }
            })
    }

}
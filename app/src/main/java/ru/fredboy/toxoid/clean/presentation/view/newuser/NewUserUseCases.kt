package ru.fredboy.toxoid.clean.presentation.view.newuser

import ru.fredboy.toxoid.clean.domain.model.LocalUser
import ru.fredboy.toxoid.clean.domain.model.ToxAddress
import ru.fredboy.toxoid.clean.domain.usecase.tox.GetOwnToxAddressUseCase
import ru.fredboy.toxoid.clean.domain.usecase.user.AddNewUserUseCase
import ru.fredboy.toxoid.clean.domain.usecase.tox.InitToxServiceUseCase
import ru.fredboy.toxoid.clean.domain.usecase.user.SetCurrentUserUseCase
import javax.inject.Inject

class NewUserUseCases @Inject constructor(
    private val addNewUserUseCase: AddNewUserUseCase,
    private val setCurrentUserUseCase: SetCurrentUserUseCase,
    private val initToxServiceUseCase: InitToxServiceUseCase,
    private val getOwnToxAddressUseCase: GetOwnToxAddressUseCase,
) {

    suspend fun getOwnToxAddress(): ToxAddress {
        return getOwnToxAddressUseCase.execute()
    }

    suspend fun addNewUser(localUser: LocalUser) {
        addNewUserUseCase.execute(localUser)
    }

    suspend fun setCurrentUserUseCase(userId: String) {
        setCurrentUserUseCase.execute(userId)
    }

    fun initToxService() {
        initToxServiceUseCase.execute()
    }

}
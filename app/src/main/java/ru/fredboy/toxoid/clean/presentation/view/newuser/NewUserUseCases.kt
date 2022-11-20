package ru.fredboy.toxoid.clean.presentation.view.newuser

import kotlinx.coroutines.flow.Flow
import ru.fredboy.toxoid.clean.domain.model.LocalUser
import ru.fredboy.toxoid.clean.domain.usecase.user.AddNewUserUseCase
import ru.fredboy.toxoid.clean.domain.usecase.tox.GetOwnToxIdFlowUseCase
import ru.fredboy.toxoid.clean.domain.usecase.user.SetCurrentUserUseCase
import ru.fredboy.toxoid.utils.ToxId
import javax.inject.Inject

class NewUserUseCases @Inject constructor(
    private val getOwnToxIdFlowUseCase: GetOwnToxIdFlowUseCase,
    private val addNewUserUseCase: AddNewUserUseCase,
    private val setCurrentUserUseCase: SetCurrentUserUseCase,
) {

    fun getOwnToxIdFlow(): Flow<ToxId> {
        return getOwnToxIdFlowUseCase.execute()
    }

    suspend fun addNewUser(localUser: LocalUser) {
        addNewUserUseCase.execute(localUser)
    }

    suspend fun setCurrentUserUseCase(userId: String) {
        setCurrentUserUseCase.execute(userId)
    }

}
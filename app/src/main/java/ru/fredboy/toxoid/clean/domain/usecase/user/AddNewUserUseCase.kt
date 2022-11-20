package ru.fredboy.toxoid.clean.domain.usecase.user

import ru.fredboy.toxoid.clean.data.repository.LocalUsersRepository
import ru.fredboy.toxoid.clean.domain.model.LocalUser
import javax.inject.Inject

class AddNewUserUseCase @Inject constructor(
    private val localUsersRepository: LocalUsersRepository
) {

    suspend fun execute(localUser: LocalUser) {
        localUsersRepository.add(localUser)
    }

}
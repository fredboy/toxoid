package ru.fredboy.toxoid.clean.domain.usecase.user

import ru.fredboy.toxoid.clean.data.repository.LocalUsersRepository
import javax.inject.Inject

class SetCurrentUserUseCase @Inject constructor(
    private val localUsersRepository: LocalUsersRepository
) {

    suspend fun execute(userId: String) {
        localUsersRepository.setCurrentId(userId)
    }

}
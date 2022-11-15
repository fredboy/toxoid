package ru.fredboy.toxoid.clean.domain.usecase

import ru.fredboy.toxoid.clean.data.repository.LocalUsersRepository
import ru.fredboy.toxoid.clean.domain.model.LocalUser
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val localUsersRepository: LocalUsersRepository,
) {

    suspend fun execute(): List<LocalUser> {
        return localUsersRepository.getAll()
    }

}
package ru.fredboy.toxoid.clean.domain.usecase

import ru.fredboy.toxoid.clean.data.repository.ContactsRepository
import javax.inject.Inject

class SetContactNameUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository,
) {

    suspend fun execute(contactId: String, newName: String) {
        contactsRepository.updateName(contactId, newName)
    }

}
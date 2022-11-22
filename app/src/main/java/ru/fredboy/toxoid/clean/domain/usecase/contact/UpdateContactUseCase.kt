package ru.fredboy.toxoid.clean.domain.usecase.contact

import ru.fredboy.toxoid.clean.data.repository.ContactsRepository
import ru.fredboy.toxoid.clean.domain.model.Contact
import javax.inject.Inject

class UpdateContactUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository,
) {

    suspend fun execute(contact: Contact) {
        contactsRepository.updateContact(contact)
    }

}
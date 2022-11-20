package ru.fredboy.toxoid.clean.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.fredboy.toxoid.clean.data.repository.ContactsRepository
import ru.fredboy.toxoid.clean.domain.model.Contact
import javax.inject.Inject

class GetContactUpdatesFlowUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository,
) {

    fun execute(): Flow<Contact> {
        return contactsRepository.getUpdatesFlow()
    }

}
package ru.fredboy.toxoid.clean.domain.usecase.contact

import ru.fredboy.toxoid.clean.data.model.tox.NewFriendNameData
import ru.fredboy.toxoid.clean.data.repository.ContactsRepository
import javax.inject.Inject

class FlowNewContactNameUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository,
) {

    fun execute(friendNameData: NewFriendNameData) {
        contactsRepository.flowNewFriendName(friendNameData)
    }

}
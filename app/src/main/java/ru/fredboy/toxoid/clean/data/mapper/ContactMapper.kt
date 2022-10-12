package ru.fredboy.toxoid.clean.data.mapper

import ru.fredboy.toxoid.clean.data.model.room.ContactEntity
import ru.fredboy.toxoid.clean.domain.model.Contact
import javax.inject.Inject

class ContactMapper @Inject constructor() {

    fun map(contactEntity: ContactEntity): Contact {
        return Contact(
            id = contactEntity.id,
            name = contactEntity.name
        )
    }

    fun map(contact: Contact): ContactEntity {
        return ContactEntity(
            id = contact.id,
            name = contact.name
        )
    }

}
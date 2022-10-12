package ru.fredboy.toxoid.clean.data.repository

import ru.fredboy.toxoid.clean.data.mapper.ContactMapper
import ru.fredboy.toxoid.clean.data.source.contact.ContactDataSource
import ru.fredboy.toxoid.clean.domain.model.Contact
import ru.fredboy.toxoid.utils.withIoDispatcher
import javax.inject.Inject

class ContactsRepository @Inject constructor(
    private val contactDataSource: ContactDataSource,
    private val contactMapper: ContactMapper
) {

    suspend fun getAll(): List<Contact> {
        return withIoDispatcher {
            contactDataSource.getAll()
                .map(contactMapper::map)
        }
    }

    suspend fun add(contact: Contact) {
        withIoDispatcher {
            val entity = contactMapper.map(contact)
            contactDataSource.add(entity)
        }
    }

}
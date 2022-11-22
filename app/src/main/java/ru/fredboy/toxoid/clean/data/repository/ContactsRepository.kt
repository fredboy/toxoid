package ru.fredboy.toxoid.clean.data.repository

import im.tox.tox4j.crypto.ToxCryptoConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.fredboy.toxoid.clean.data.mapper.ContactMapper
import ru.fredboy.toxoid.clean.data.model.room.ContactEntity
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

    suspend fun get(contactId: String): Contact? {
        return withIoDispatcher {
            contactDataSource.getById(contactId)
                ?.let(contactMapper::map)
        }
    }

    suspend fun add(contact: Contact) {
        withIoDispatcher {
            val entity = contactMapper.map(contact)
            contactDataSource.add(entity)
        }
    }

    suspend fun updateName(contactId: String, newName: String) {
        withIoDispatcher {
            contactDataSource.update(ContactEntity(contactId, newName))
        }
    }

    fun getUpdatesFlow(): Flow<Contact> {
        return contactDataSource.getUpdatesFlow()
            .flowOn(Dispatchers.IO)
            .map(contactMapper::map)
    }

    suspend fun createForToxId(toxId: String): Contact {
        return withIoDispatcher {
            // 2 chars per byte
            val privKey = toxId.substring(0 until ToxCryptoConstants.PublicKeyLength() * 2)
            val exists = contactDataSource.getById(privKey) != null
            if (exists) {
                throw IllegalArgumentException("Contact with id $toxId already exists")
            }

            val contact = Contact(id = privKey, name = toxId)
            add(contact)

            contact
        }
    }

}
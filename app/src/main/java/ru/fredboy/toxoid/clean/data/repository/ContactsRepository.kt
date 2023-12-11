package ru.fredboy.toxoid.clean.data.repository

import im.tox.tox4j.crypto.ToxCryptoConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.fredboy.toxoid.clean.data.mapper.ContactMapper
import ru.fredboy.toxoid.clean.data.model.tox.NewFriendNameData
import ru.fredboy.toxoid.clean.data.source.contact.ContactDataSource
import ru.fredboy.toxoid.clean.data.source.intent.ToxServiceIntentApi
import ru.fredboy.toxoid.clean.data.source.tox.ToxEventDataSource
import ru.fredboy.toxoid.clean.domain.model.Contact
import ru.fredboy.toxoid.clean.domain.model.ToxPublicKey
import ru.fredboy.toxoid.utils.withIoDispatcher
import javax.inject.Inject

class ContactsRepository @Inject constructor(
    private val contactDataSource: ContactDataSource,
    private val contactMapper: ContactMapper,
    private val toxServiceIntentApi: ToxServiceIntentApi,
    private val toxEventDataSource: ToxEventDataSource,
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

    private suspend fun resolveFriendNumberToPublicKey(friendNumber: Int): ToxPublicKey {
        return withIoDispatcher {
            toxServiceIntentApi.resolveFriendNumber(friendNumber)
        }
    }

    suspend fun updateContact(contact: Contact) {
        withIoDispatcher {
            val contactEntity = contactMapper.map(contact)
            contactDataSource.update(contactEntity)
        }
    }

    fun flowNewFriendName(friendNameData: NewFriendNameData) {
        toxEventDataSource.flowNewFriendNameData(friendNameData)
    }

    fun getContactNameUpdatesFlow(): Flow<Contact> {
        return toxEventDataSource.getNewFriendNameDataFlow()
            .flowOn(Dispatchers.IO)
            .map { data ->
                val friendPublicKey = resolveFriendNumberToPublicKey(data.friendNumber)
                val newFriendName = String(data.newFriendNameBytes)
                friendPublicKey to newFriendName
            }
            .map { (friendPublicKey, newFriendName) ->
                val contact = Contact(friendPublicKey.toString(), newFriendName)
                contact
            }
    }

    suspend fun createForToxId(toxId: String): Contact {
        return withIoDispatcher {
            // 2 chars per byte
            val privKey = toxId.substring(0 ..< ToxCryptoConstants.publicKeyLength * 2)
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
package ru.fredboy.toxoid.clean.data.source.contact

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.fredboy.toxoid.clean.data.model.room.ContactEntity
import ru.fredboy.toxoid.clean.data.source.room.MainDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomContactDataSource @Inject constructor(
    private val mainDatabase: MainDatabase
) : ContactDataSource {

    private val contactUpdatesFlow = MutableSharedFlow<ContactEntity>(
        extraBufferCapacity = 10
    )

    override suspend fun getById(contactId: String): ContactEntity? {
        return mainDatabase.contactDao.getById(contactId)
    }

    override suspend fun getAll(): List<ContactEntity> {
        return mainDatabase.contactDao.getAll()
    }

    override suspend fun add(contact: ContactEntity) {
        mainDatabase.contactDao.insert(contact)
    }

    override suspend fun update(contact: ContactEntity) {
        mainDatabase.contactDao.insert(contact)
        contactUpdatesFlow.tryEmit(contact)
    }

    override fun getUpdatesFlow(): Flow<ContactEntity> {
        return contactUpdatesFlow
    }
}
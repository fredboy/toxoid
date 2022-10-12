package ru.fredboy.toxoid.clean.data.source.contact

import ru.fredboy.toxoid.clean.data.model.room.ContactEntity
import ru.fredboy.toxoid.clean.data.source.room.MainDatabase
import javax.inject.Inject

class RoomContactDataSource @Inject constructor(
    private val mainDatabase: MainDatabase
) : ContactDataSource {

    override suspend fun getById(contactId: String): ContactEntity? {
        return mainDatabase.contactDao.getById(contactId)
    }

    override suspend fun getAll(): List<ContactEntity> {
        return mainDatabase.contactDao.getAll()
    }

    override suspend fun add(contact: ContactEntity) {
        mainDatabase.contactDao.insert(contact)
    }
}
package ru.fredboy.toxoid.clean.data.source.contact

import ru.fredboy.toxoid.clean.data.model.room.ContactEntity

interface ContactDataSource {

    suspend fun getById(contactId: String): ContactEntity?

    suspend fun getAll(): List<ContactEntity>

    suspend fun add(contact: ContactEntity)

}
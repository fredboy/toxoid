package ru.fredboy.toxoid.clean.data.repository

import ru.fredboy.toxoid.clean.data.mapper.LocalUserMapper
import ru.fredboy.toxoid.clean.data.source.contact.ContactDataSource
import ru.fredboy.toxoid.clean.data.source.user.CurrentLocalUserDataSource
import ru.fredboy.toxoid.clean.data.source.user.LocalUserDataSource
import ru.fredboy.toxoid.clean.domain.model.LocalUser
import ru.fredboy.toxoid.utils.withIoDispatcher
import javax.inject.Inject

class LocalUsersRepository @Inject constructor(
    private val localUserDataSource: LocalUserDataSource,
    private val currentLocalUserDataSource: CurrentLocalUserDataSource,
    private val localUserMapper: LocalUserMapper,
    private val contactDataSource: ContactDataSource
) {

    suspend fun getAll(): List<LocalUser> {
        return withIoDispatcher {
            localUserDataSource.getAll()
                .map { entity -> localUserMapper.map(entity) }
        }
    }

    suspend fun getCurrent(): LocalUser? {
        return withIoDispatcher {
            currentLocalUserDataSource.getCurrentLocalUserId()
                ?.let { id -> localUserDataSource.getById(id) }
                ?.let { entity -> localUserMapper.map(entity) }
        }
    }

    suspend fun setCurrentId(userId: String) {
        withIoDispatcher {
            currentLocalUserDataSource.setCurrentLocalUserId(userId)
        }
    }

    suspend fun add(user: LocalUser) {
        withIoDispatcher {
            val entity = localUserMapper.map(user)
            localUserDataSource.add(entity)
            contactDataSource.add(entity.toContactEntity())
        }
    }

}
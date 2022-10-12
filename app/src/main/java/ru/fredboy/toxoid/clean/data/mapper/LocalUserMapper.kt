package ru.fredboy.toxoid.clean.data.mapper

import ru.fredboy.toxoid.clean.data.model.room.LocalUserEntity
import ru.fredboy.toxoid.clean.domain.model.LocalUser
import javax.inject.Inject

class LocalUserMapper @Inject constructor() {

    fun map(entity: LocalUserEntity): LocalUser {
        return LocalUser(
            id = entity.id,
            name = entity.name
        )
    }

    fun map(user: LocalUser): LocalUserEntity {
        return LocalUserEntity(
            id = user.id,
            name = user.name
        )
    }

}
package ru.fredboy.toxoid.clean.data.mapper

import ru.fredboy.toxoid.clean.data.model.FriendRequestData
import ru.fredboy.toxoid.clean.data.model.room.FriendRequestEntity
import ru.fredboy.toxoid.clean.domain.model.FriendRequest
import ru.fredboy.toxoid.utils.bytesToHexString
import javax.inject.Inject

class FriendRequestMapper @Inject constructor(
    private val toxAddressMapper: ToxAddressMapper,
) {

    fun map(request: FriendRequestEntity): FriendRequest {
        return FriendRequest(
            friendAddress = toxAddressMapper.map(request.toxid),
            message = request.message
        )
    }

    fun map(request: FriendRequest): FriendRequestEntity {
        return FriendRequestEntity(
            toxid = request.friendAddress.toString(),
            message = request.message
        )
    }

    fun map(data: FriendRequestData): FriendRequestEntity {
        val toxId = bytesToHexString(data.publicKeyBytes)
        val message = String(data.messageBytes)
        return FriendRequestEntity(
            toxid = toxId,
            message = message
        )
    }

}
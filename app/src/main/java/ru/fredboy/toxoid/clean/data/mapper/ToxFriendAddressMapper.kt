package ru.fredboy.toxoid.clean.data.mapper

import ru.fredboy.toxoid.clean.domain.model.ToxFriendAddress
import ru.fredboy.toxoid.utils.bytesToHexString
import ru.fredboy.toxoid.utils.hexStringToByteArray
import javax.inject.Inject

class ToxFriendAddressMapper @Inject constructor() {
    
    fun map(toxId: String): ToxFriendAddress {
        return map(hexStringToByteArray(toxId))
    }

    fun map(address: ToxFriendAddress): String {
        return bytesToHexString(address.bytes)
    }

    fun map(bytes: ByteArray): ToxFriendAddress {
        return ToxFriendAddress(bytes)
    }
    
}
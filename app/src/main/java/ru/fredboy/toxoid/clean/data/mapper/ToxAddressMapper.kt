package ru.fredboy.toxoid.clean.data.mapper

import ru.fredboy.toxoid.clean.domain.model.ToxAddress
import ru.fredboy.toxoid.utils.hexStringToByteArray
import javax.inject.Inject

class ToxAddressMapper @Inject constructor() {
    
    fun map(toxId: String): ToxAddress {
        return map(hexStringToByteArray(toxId))
    }

    fun map(bytes: ByteArray): ToxAddress {
        return ToxAddress(bytes)
    }
    
}
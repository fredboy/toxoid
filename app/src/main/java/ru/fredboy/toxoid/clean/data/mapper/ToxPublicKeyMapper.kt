package ru.fredboy.toxoid.clean.data.mapper

import ru.fredboy.toxoid.clean.domain.model.ToxPublicKey
import ru.fredboy.toxoid.utils.hexStringToByteArray
import javax.inject.Inject

class ToxPublicKeyMapper @Inject constructor() {

    fun map(toxId: String): ToxPublicKey {
        return map(hexStringToByteArray(toxId))
    }

    fun map(publicKeyBytes: ByteArray): ToxPublicKey {
        return ToxPublicKey(publicKeyBytes)
    }
}
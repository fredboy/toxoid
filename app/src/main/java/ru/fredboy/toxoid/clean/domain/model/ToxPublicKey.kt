package ru.fredboy.toxoid.clean.domain.model

import ru.fredboy.tox4a.api.crypto.ToxCryptoConstants
import ru.fredboy.toxoid.utils.bytesToHexString
import java.io.Serializable

data class ToxPublicKey(val bytes: ByteArray) : Serializable {

    init {
        assert(bytes.size == ToxCryptoConstants.publicKeyLength)
    }


    override fun toString() = bytesToHexString(bytes)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ToxPublicKey

        return bytes.contentEquals(other.bytes)
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }
}
package ru.fredboy.toxoid.clean.domain.model

import im.tox.tox4j.crypto.ToxCryptoConstants
import ru.fredboy.toxoid.utils.bytesToHexString
import java.io.Serializable

data class ToxPublicKey(val bytes: ByteArray) : Serializable {

    init {
        assert(bytes.size == ToxCryptoConstants.PublicKeyLength())
    }


    override fun toString() = bytesToHexString(bytes)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ToxPublicKey

        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }
}
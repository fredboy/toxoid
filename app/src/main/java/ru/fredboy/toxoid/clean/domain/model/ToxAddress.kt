package ru.fredboy.toxoid.clean.domain.model

import im.tox.tox4j.core.ToxCoreConstants
import im.tox.tox4j.crypto.ToxCryptoConstants
import ru.fredboy.toxoid.utils.bytesToHexString
import java.io.Serializable

data class ToxAddress(val bytes: ByteArray) : Serializable {

    init {
        assert(bytes.size == ToxCoreConstants.addressSize)
    }

    fun toToxPublicKey(): ToxPublicKey =
        ToxPublicKey(bytes.sliceArray(0 until ToxCryptoConstants.publicKeyLength))

    override fun toString() = bytesToHexString(bytes)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ToxAddress

        return bytes.contentEquals(other.bytes)
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }

}
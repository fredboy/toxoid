package ru.fredboy.toxoid.clean.domain.model

import im.tox.tox4j.core.ToxCoreConstants
import im.tox.tox4j.crypto.ToxCryptoConstants
import ru.fredboy.toxoid.utils.bytesToHexString
import java.io.Serializable

data class ToxFriendAddress(val bytes: ByteArray) : Serializable {

    init {
        assert(bytes.size == ToxCoreConstants.AddressSize())
    }

    fun toToxPublicKey(): ToxPublicKey =
        ToxPublicKey(bytes.sliceArray(0 until ToxCryptoConstants.PublicKeyLength()))

    fun toHexString() = bytesToHexString(bytes)

}
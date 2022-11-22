package ru.fredboy.toxoid.clean.domain.model

import im.tox.tox4j.crypto.ToxCryptoConstants
import ru.fredboy.toxoid.utils.bytesToHexString
import java.io.Serializable

data class ToxPublicKey(val bytes: ByteArray) : Serializable {

    init {
        assert(bytes.size == ToxCryptoConstants.PublicKeyLength())
    }


    fun toHexString() = bytesToHexString(bytes)
}
package ru.fredboy.toxoid.clean.data.mapper

import im.tox.tox4j.core.data.ToxPublicKey
import ru.fredboy.toxoid.utils.bytesToHexString
import ru.fredboy.toxoid.utils.hexStringToByteArray
import javax.inject.Inject

class ToxPublicKeyMapper @Inject constructor() {

    fun map(toxId: String): ToxPublicKey {
        return map(hexStringToByteArray(toxId))
    }

    fun map(publicKey: ToxPublicKey): String {
        return bytesToHexString(publicKey.value())
    }

    fun map(publicKeyBytes: ByteArray): ToxPublicKey {
        return ToxPublicKey(publicKeyBytes)
    }
}
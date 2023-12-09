package ru.fredboy.tox4a.api.crypto

interface ToxCrypto<PassKey> {

    fun passKeyEquals(a: PassKey, b: PassKey): Boolean

    fun passKeyToBytes(passKey: PassKey): ByteArray

    fun passKeyFromBytes(bytes: ByteArray): PassKey?

    fun passKeyDerive(passPhrase: ByteArray): PassKey

    fun passKeyDeriveWithSalt(passPhrase: ByteArray, salt: ByteArray): PassKey

    fun getSalt(data: ByteArray): ByteArray

    fun encrypt(data: ByteArray, passKey: PassKey): ByteArray

    fun decrypt(data: ByteArray, passKey: PassKey): ByteArray

    fun isDataEncrypted(data: ByteArray): Boolean

    fun hash(data: ByteArray): ByteArray

}
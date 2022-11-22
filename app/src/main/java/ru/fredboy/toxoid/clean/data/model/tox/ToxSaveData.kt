package ru.fredboy.toxoid.clean.data.model.tox

import java.io.Serializable

data class ToxSaveData(
    val ownAddressBytes: ByteArray,
    val toxSaveData: ByteArray,
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ToxSaveData

        if (!ownAddressBytes.contentEquals(other.ownAddressBytes)) return false
        if (!toxSaveData.contentEquals(other.toxSaveData)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = ownAddressBytes.contentHashCode()
        result = 31 * result + toxSaveData.contentHashCode()
        return result
    }
}

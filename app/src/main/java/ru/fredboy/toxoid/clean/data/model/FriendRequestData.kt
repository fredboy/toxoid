package ru.fredboy.toxoid.clean.data.model

data class FriendRequestData(
    val publicKeyBytes: ByteArray,
    val messageBytes: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FriendRequestData

        if (!publicKeyBytes.contentEquals(other.publicKeyBytes)) return false
        if (!messageBytes.contentEquals(other.messageBytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = publicKeyBytes.contentHashCode()
        result = 31 * result + messageBytes.contentHashCode()
        return result
    }
}

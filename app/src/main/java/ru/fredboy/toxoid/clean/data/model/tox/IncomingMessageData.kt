package ru.fredboy.toxoid.clean.data.model.tox

data class IncomingMessageData(
    val friendNumber: Int,
    val messageBytes: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IncomingMessageData

        if (friendNumber != other.friendNumber) return false
        if (!messageBytes.contentEquals(other.messageBytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = friendNumber
        result = 31 * result + messageBytes.contentHashCode()
        return result
    }
}

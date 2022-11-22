package ru.fredboy.toxoid.clean.data.model.tox

data class NewFriendNameData(
    val friendNumber: Int,
    val newFriendNameBytes: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NewFriendNameData

        if (friendNumber != other.friendNumber) return false
        if (!newFriendNameBytes.contentEquals(other.newFriendNameBytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = friendNumber
        result = 31 * result + newFriendNameBytes.contentHashCode()
        return result
    }
}

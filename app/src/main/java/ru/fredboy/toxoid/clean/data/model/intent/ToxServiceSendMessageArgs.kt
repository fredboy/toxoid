package ru.fredboy.toxoid.clean.data.model.intent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ToxServiceSendMessageArgs(
    val recipientPublicKeyBytes: ByteArray,
    val messageBytes: ByteArray,
    val timestamp: Long,
) : ToxServiceArgs, Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ToxServiceSendMessageArgs

        if (!recipientPublicKeyBytes.contentEquals(other.recipientPublicKeyBytes)) return false
        if (!messageBytes.contentEquals(other.messageBytes)) return false
        if (timestamp != other.timestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = recipientPublicKeyBytes.contentHashCode()
        result = 31 * result + messageBytes.contentHashCode()
        result = 31 * result + timestamp.hashCode()
        return result
    }

    companion object {
        const val PARCEL_KEY = "ToxServiceSendMessageArgsParcel"
    }
}

package ru.fredboy.toxoid.clean.data.model.intent.args

import android.os.ResultReceiver
import kotlinx.parcelize.Parcelize

@Parcelize
data class ToxServiceAddFriendArgs(
    val friendAddressBytes: ByteArray,
    val messageBytes: ByteArray,
    override val resultReceiver: ResultReceiver,
) : ToxServiceArgs {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ToxServiceAddFriendArgs

        if (!friendAddressBytes.contentEquals(other.friendAddressBytes)) return false
        if (!messageBytes.contentEquals(other.messageBytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = friendAddressBytes.contentHashCode()
        result = 31 * result + messageBytes.contentHashCode()
        return result
    }

    companion object {
        const val PARCEL_KEY = "ToxServiceAddFriendArgsParcel"
    }
}

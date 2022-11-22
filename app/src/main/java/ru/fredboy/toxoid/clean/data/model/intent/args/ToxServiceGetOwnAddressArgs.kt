package ru.fredboy.toxoid.clean.data.model.intent.args

import android.os.ResultReceiver
import kotlinx.parcelize.Parcelize

@Parcelize
data class ToxServiceGetOwnAddressArgs(
    override val resultReceiver: ResultReceiver
) : ToxServiceArgs {
    companion object {
        const val PARCEL_KEY = "ToxServiceGetOwnAddressArgsParcel"
    }
}

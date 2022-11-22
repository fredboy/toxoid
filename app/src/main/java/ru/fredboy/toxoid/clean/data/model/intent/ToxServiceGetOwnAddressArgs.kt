package ru.fredboy.toxoid.clean.data.model.intent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
object ToxServiceGetOwnAddressArgs : ToxServiceArgs, Parcelable {
    const val PARCEL_KEY = "ToxServiceGetOwnAddressArgsParcel"
}

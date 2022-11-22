package ru.fredboy.toxoid.clean.data.model.intent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.fredboy.toxoid.clean.domain.model.ToxAddress

@Parcelize
data class ToxServiceGetOwnAddressResult(
    val address: ToxAddress
) : Parcelable {
    companion object {
        const val PARCEL_KEY = "ToxServiceGetOwnAddressResultParcel"
    }
}

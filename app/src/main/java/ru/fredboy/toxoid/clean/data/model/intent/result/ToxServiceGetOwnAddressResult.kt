package ru.fredboy.toxoid.clean.data.model.intent.result

import kotlinx.parcelize.Parcelize
import ru.fredboy.toxoid.clean.domain.model.ToxAddress

@Parcelize
data class ToxServiceGetOwnAddressResult(
    val address: ToxAddress,
    override val error: Throwable? = null
) : ToxServiceResult {
    companion object {
        const val PARCEL_KEY = "ToxServiceGetOwnAddressResultParcel"
    }
}

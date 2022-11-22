package ru.fredboy.toxoid.clean.data.model.intent.result

import kotlinx.parcelize.Parcelize

@Parcelize
data class ToxServiceSendMessageResult(
    override val error: Throwable? = null
) : ToxServiceResult {
    companion object {
        const val PARCEL_KEY = "ToxServiceSendMessageResultParcel"
    }
}

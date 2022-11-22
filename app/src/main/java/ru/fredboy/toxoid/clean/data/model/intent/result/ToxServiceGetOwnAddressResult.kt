package ru.fredboy.toxoid.clean.data.model.intent.result

import kotlinx.parcelize.Parcelize
import ru.fredboy.toxoid.clean.domain.model.ToxAddress

@Parcelize
data class ToxServiceGetOwnAddressResult(
    val address: ToxAddress,
) : ToxServiceResult

package ru.fredboy.toxoid.clean.data.model.intent.result

import kotlinx.parcelize.Parcelize
import ru.fredboy.toxoid.clean.domain.model.ToxPublicKey

@Parcelize
data class ToxServiceResolveFriendNumberResult(
    val friendPublicKey: ToxPublicKey,
) : ToxServiceResult

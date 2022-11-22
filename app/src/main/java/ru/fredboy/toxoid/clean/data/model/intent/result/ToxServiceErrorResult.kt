package ru.fredboy.toxoid.clean.data.model.intent.result

import kotlinx.parcelize.Parcelize

@Parcelize
data class ToxServiceErrorResult(
    val error: Throwable
) : ToxServiceResult

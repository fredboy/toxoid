package ru.fredboy.toxoid.clean.data.model.intent.result

import kotlinx.parcelize.Parcelize
import ru.fredboy.toxoid.clean.data.model.tox.ToxSaveData

@Parcelize
data class ToxServiceAddFriendResult(
    val toxSaveData: ToxSaveData,
) : ToxServiceResult
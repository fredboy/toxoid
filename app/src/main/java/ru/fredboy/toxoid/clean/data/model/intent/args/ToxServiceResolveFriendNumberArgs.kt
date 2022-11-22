package ru.fredboy.toxoid.clean.data.model.intent.args

import android.os.ResultReceiver
import kotlinx.parcelize.Parcelize

@Parcelize
data class ToxServiceResolveFriendNumberArgs(
    val friendNumber: Int,
    override val resultReceiver: ResultReceiver
) : ToxServiceArgs

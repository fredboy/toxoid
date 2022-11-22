package ru.fredboy.toxoid.clean.data.model.intent.args

import android.os.Parcelable
import android.os.ResultReceiver

sealed interface ToxServiceArgs : Parcelable {
    val resultReceiver: ResultReceiver
}
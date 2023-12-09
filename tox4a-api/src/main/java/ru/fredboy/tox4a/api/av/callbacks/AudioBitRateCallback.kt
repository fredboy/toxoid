package ru.fredboy.tox4a.api.av.callbacks

import ru.fredboy.tox4a.api.av.data.BitRate
import ru.fredboy.tox4a.api.core.data.ToxFriendNumber

fun interface AudioBitRateCallback {

    fun audioBitRate(friendNumber: ToxFriendNumber, bitRate: BitRate)

}
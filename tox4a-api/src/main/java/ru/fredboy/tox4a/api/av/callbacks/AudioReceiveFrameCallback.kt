package ru.fredboy.tox4a.api.av.callbacks

import ru.fredboy.tox4a.api.av.data.AudioChannels
import ru.fredboy.tox4a.api.av.data.SamplingRate
import ru.fredboy.tox4a.api.core.data.ToxFriendNumber

fun interface AudioReceiveFrameCallback {

    fun audioReceiveFrame(
        friendNumber: ToxFriendNumber,
        pcm: ShortArray,
        channels: AudioChannels,
        samplingRate: SamplingRate
    )

}
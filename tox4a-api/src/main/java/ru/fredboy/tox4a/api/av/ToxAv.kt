package ru.fredboy.tox4a.api.av

import ru.fredboy.tox4a.api.av.callbacks.ToxavEventListener
import ru.fredboy.tox4a.api.av.data.AudioChannels
import ru.fredboy.tox4a.api.av.data.BitRate
import ru.fredboy.tox4a.api.av.data.SampleCount
import ru.fredboy.tox4a.api.av.data.SamplingRate
import ru.fredboy.tox4a.api.av.data.enums.ToxavCallControl
import ru.fredboy.tox4a.api.core.ToxCore
import ru.fredboy.tox4a.api.core.data.ToxFriendNumber
import java.io.Closeable

interface ToxAv : Closeable {

    fun create(tox: ToxCore): ToxAv

    fun getIterationInterval(): Int

    fun iterate(handler: ToxavEventListener)

    fun call(friendNumber: ToxFriendNumber, audioBitRate: BitRate, videoBitRate: BitRate)

    fun answer(friendNumber: ToxFriendNumber, audioBitRate: BitRate, videoBitRate: BitRate)

    fun callControl(friendNumber: ToxFriendNumber, control: ToxavCallControl)

    fun setAudioBitRate(friendNumber: ToxFriendNumber, audioBitRate: BitRate)

    fun setVideoBitRate(friendNumber: ToxFriendNumber, videoBitRate: BitRate)

    fun audioSendFrame(
        friendNumber: ToxFriendNumber,
        pcm: ShortArray,
        sampleCount: SampleCount,
        channels: AudioChannels,
        samplingRate: SamplingRate
    )

    fun videoSendFrame(
        friendNumber: ToxFriendNumber,
        width: Int,
        height: Int,
        y: ByteArray,
        u: ByteArray,
        v: ByteArray
    )
}
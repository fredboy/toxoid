package ru.fredboy.tox4a.api.av.callbacks

import ru.fredboy.tox4a.api.av.data.AudioChannels
import ru.fredboy.tox4a.api.av.data.BitRate
import ru.fredboy.tox4a.api.av.data.Height
import ru.fredboy.tox4a.api.av.data.SamplingRate
import ru.fredboy.tox4a.api.av.data.Width
import ru.fredboy.tox4a.api.av.data.enums.ToxavFriendCallState
import ru.fredboy.tox4a.api.core.data.ToxFriendNumber
import java.util.EnumSet

class CompositeToxavEventListener(
    private val audioBitRateCallback: AudioBitRateCallback? = null,
    private val audioReceiveFrameCallback: AudioReceiveFrameCallback? = null,
    private val callCallback: CallCallback? = null,
    private val callStateCallback: CallStateCallback? = null,
    private val videoBitRateCallback: VideoBitRateCallback? = null,
    private val videoReceiveFrameCallback: VideoReceiveFrameCallback? = null,
) : ToxavEventListener {

    override fun audioBitRate(friendNumber: ToxFriendNumber, bitRate: BitRate) {
        audioBitRateCallback?.audioBitRate(
            friendNumber = friendNumber,
            bitRate = bitRate
        )
    }

    override fun audioReceiveFrame(
        friendNumber: ToxFriendNumber,
        pcm: ShortArray,
        channels: AudioChannels,
        samplingRate: SamplingRate
    ) {
        audioReceiveFrameCallback?.audioReceiveFrame(
            friendNumber = friendNumber,
            pcm = pcm,
            channels = channels,
            samplingRate = samplingRate
        )
    }

    override fun call(friendNumber: ToxFriendNumber, audioEnabled: Boolean, videoEnabled: Boolean) {
        callCallback?.call(
            friendNumber = friendNumber,
            audioEnabled = audioEnabled,
            videoEnabled = videoEnabled
        )
    }

    override fun callState(
        friendNumber: ToxFriendNumber,
        callState: EnumSet<ToxavFriendCallState>
    ) {
        callStateCallback?.callState(
            friendNumber = friendNumber,
            callState = callState
        )
    }

    override fun videoBitRate(friendNumber: ToxFriendNumber, videoBitRate: BitRate) {
        videoBitRateCallback?.videoBitRate(
            friendNumber = friendNumber,
            videoBitRate = videoBitRate
        )
    }

    override fun videoReceiveFrame(
        friendNumber: ToxFriendNumber,
        width: Width,
        height: Height,
        y: ByteArray,
        v: ByteArray,
        u: ByteArray,
        yStride: Int,
        vStride: Int,
        uStride: Int
    ) {
        videoReceiveFrameCallback?.videoReceiveFrame(
            friendNumber = friendNumber,
            width = width,
            height = height,
            y = y,
            v = v,
            u = u,
            yStride = yStride,
            vStride = vStride,
            uStride = uStride
        )
    }

    override fun videoFrameCachedYUV(
        height: Height,
        yStride: Int,
        vStride: Int,
        uStride: Int
    ): Triple<ByteArray, ByteArray, ByteArray>? {
        return videoReceiveFrameCallback?.videoFrameCachedYUV(
            height = height,
            yStride = yStride,
            vStride = vStride,
            uStride = uStride
        )
    }
}
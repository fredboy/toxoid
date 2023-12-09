package ru.fredboy.tox4a.api.av.callbacks

import ru.fredboy.tox4a.api.av.data.Height
import ru.fredboy.tox4a.api.av.data.Width
import ru.fredboy.tox4a.api.core.data.ToxFriendNumber

fun interface VideoReceiveFrameCallback {

    fun videoReceiveFrame(
        friendNumber: ToxFriendNumber,
        width: Width,
        height: Height,
        y: ByteArray,
        v: ByteArray,
        u: ByteArray,
        yStride: Int,
        vStride: Int,
        uStride: Int,
    )

    fun videoFrameCachedYUV(
        height: Height,
        yStride: Int,
        vStride: Int,
        uStride: Int,
    ) : Triple<ByteArray, ByteArray, ByteArray>? = null

}
package ru.fredboy.tox4a.api.core.callbacks

import ru.fredboy.tox4a.api.core.data.ToxFriendNumber

fun interface FileRecvChunkCallback {

    fun fileRecvChunk(
        toxFriendNumber: ToxFriendNumber,
        fileNumber: Int,
        position: Long,
        data: ByteArray
    )

}
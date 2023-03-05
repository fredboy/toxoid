package ru.fredboy.tox4a.core.callbacks

import ru.fredboy.tox4a.core.data.ToxFriendNumber

fun interface FileChunkRequestCallback {

    fun fileChunkRequest(
        toxFriendNumber: ToxFriendNumber,
        fileNumber: Int,
        position: Long,
        length: Int
    )

}
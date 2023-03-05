package ru.fredboy.tox4a.core.callbacks

import ru.fredboy.tox4a.core.data.ToxFileName
import ru.fredboy.tox4a.core.data.ToxFriendNumber
import ru.fredboy.tox4a.core.data.enums.ToxFileKind

fun interface FileRecvCallback {

    fun fileRecv(
        toxFriendNumber: ToxFriendNumber,
        fileNumber: Int,
        kind: ToxFileKind,
        fileSize: Long,
        filename: ToxFileName
    )

}
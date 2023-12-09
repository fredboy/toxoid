package ru.fredboy.tox4a.api.core.callbacks

import ru.fredboy.tox4a.api.core.data.ToxFileName
import ru.fredboy.tox4a.api.core.data.ToxFriendNumber
import ru.fredboy.tox4a.api.core.data.enums.ToxFileKind

fun interface FileRecvCallback {

    fun fileRecv(
        toxFriendNumber: ToxFriendNumber,
        fileNumber: Int,
        kind: ToxFileKind,
        fileSize: Long,
        filename: ToxFileName
    )

}
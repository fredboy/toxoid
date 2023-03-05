package ru.fredboy.tox4a.core.callbacks

import ru.fredboy.tox4a.core.data.ToxFriendNumber
import ru.fredboy.tox4a.core.data.enums.ToxFileControl

fun interface FileRecvControlCallback {

    fun fileRecvControl(
        toxFriendNumber: ToxFriendNumber,
        fileNumber: Int,
        control: ToxFileControl
    )

}
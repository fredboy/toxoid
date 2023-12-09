package ru.fredboy.tox4a.api.core.callbacks

import ru.fredboy.tox4a.api.core.data.ToxFriendNumber
import ru.fredboy.tox4a.api.core.data.enums.ToxFileControl

fun interface FileRecvControlCallback {

    fun fileRecvControl(
        toxFriendNumber: ToxFriendNumber,
        fileNumber: Int,
        control: ToxFileControl
    )

}
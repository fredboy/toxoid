package ru.fredboy.tox4a.core.callbacks

import ru.fredboy.tox4a.core.data.enums.ToxConnection

fun interface SelfConnectionStatusCallback {

    fun selfConnectionStatus(
        connectionStatus: ToxConnection
    )

}
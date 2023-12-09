package ru.fredboy.tox4a.api.core.callbacks

import ru.fredboy.tox4a.api.core.data.enums.ToxConnection

fun interface SelfConnectionStatusCallback {

    fun selfConnectionStatus(
        connectionStatus: ToxConnection
    )

}
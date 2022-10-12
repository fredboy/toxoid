package ru.fredboy.toxoid.clean.domain.model

import im.tox.tox4j.core.data.ToxPublicKey

data class BootstrapNode(
    val publicKey: ToxPublicKey,
    val host: String,
    val port: Int,
    val location: String,
    val status: Boolean? = null,
    val motd: String? = null
)

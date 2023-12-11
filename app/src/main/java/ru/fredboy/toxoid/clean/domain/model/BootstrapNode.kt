package ru.fredboy.toxoid.clean.domain.model

data class BootstrapNode(
    val publicKey: ToxPublicKey,
    val host: String,
    val port: UShort,
    val location: String,
    val status: Boolean? = null,
    val motd: String? = null
)

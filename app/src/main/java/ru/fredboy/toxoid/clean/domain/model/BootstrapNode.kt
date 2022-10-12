package ru.fredboy.toxoid.clean.domain.model

data class BootstrapNode(
    val ipv4: String,
    val ipv6: String?,
    val location: String,
    val status: Boolean
)

package ru.fredboy.toxoid.clean.data.model.json

import kotlinx.serialization.Serializable

@Serializable
data class BootstrapNodeDto(
    val ipv4: String,
    val port: Int,

)

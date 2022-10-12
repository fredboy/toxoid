package ru.fredboy.toxoid.clean.presentation.model

data class BootstrapNodeVo(
    val socket: String,
    val flag: String,
    val status: Boolean,
    val motd: String,
)
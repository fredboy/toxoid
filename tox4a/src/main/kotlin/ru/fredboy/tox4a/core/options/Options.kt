package ru.fredboy.tox4a.core.options

data class Options(
    val ipv6Enabled: Boolean = true,
    val udpEnabled: Boolean = true,
    val localDiscoveryEnabled: Boolean = true,
) {
    init {
        TODO()
    }
}
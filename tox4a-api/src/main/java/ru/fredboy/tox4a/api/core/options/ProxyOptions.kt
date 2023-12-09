package ru.fredboy.tox4a.api.core.options

import ru.fredboy.tox4a.api.core.data.enums.ToxProxyType

data class ProxyOptions(
    val proxyType: ToxProxyType,
    val proxyAddress: String,
    val proxyPort: UShort
) {

    companion object {
        val NONE = ProxyOptions(
            proxyType = ToxProxyType.NONE,
            proxyAddress = "",
            proxyPort = 0u
        )

        fun http(proxyAddress: String, proxyPort: UShort) =
            ProxyOptions(
                proxyType = ToxProxyType.HTTP,
                proxyAddress = proxyAddress,
                proxyPort = proxyPort
            )

        fun socks5(proxyAddress: String, proxyPort: UShort) =
            ProxyOptions(
                proxyType = ToxProxyType.SOCKS5,
                proxyAddress = proxyAddress,
                proxyPort = proxyPort
            )
    }
}
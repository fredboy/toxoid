package ru.fredboy.tox4a.api.core.options

import ru.fredboy.tox4a.api.core.ToxCoreConstants

data class ToxOptions(
    val ipv6Enabled: Boolean = true,
    val udpEnabled: Boolean = true,
    val localDiscoveryEnabled: Boolean = true,
    val proxyOptions: ProxyOptions = ProxyOptions.NONE,
    val startPort: UShort = ToxCoreConstants.defaultStartPort,
    val endPort: UShort = ToxCoreConstants.defaultEndPort,
    val tcpPort: UShort = ToxCoreConstants.defaultTcpPort,
    val saveData: SaveDataOptions = SaveDataOptions.NONE,
    val fatalErrors: Boolean = true,
)
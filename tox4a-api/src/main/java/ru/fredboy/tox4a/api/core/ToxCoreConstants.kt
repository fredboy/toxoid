package ru.fredboy.tox4a.api.core

import ru.fredboy.tox4a.api.crypto.ToxCryptoConstants

object ToxCoreConstants {

    const val publicKeySize = ToxCryptoConstants.publicKeyLength

    const val secretKeySize = ToxCryptoConstants.secretKeyLength

    const val addressSize = publicKeySize + 4 + 2

    const val maxNameLength = 128

    const val maxStatusMessageLength = 1007

    const val maxFriendRequestLength = 1016

    const val maxMessageLength = 1372

    const val maxCustomPacketLength = 1373

    const val maxFileNameLength = 255

    const val maxHostNameLength = 255

    const val fileIdLength = ToxCryptoConstants.hashLength

    const val defaultProxyPort: UShort = 8080u

    const val defaultStartPort: UShort = 33445u

    const val defaultEndPort: UShort = 33545u

    const val defaultTcpPort: UShort = 0u

}
package ru.fredboy.toxoid.clean.presentation.formatter

import ru.fredboy.toxoid.clean.domain.model.BootstrapNode
import ru.fredboy.toxoid.clean.presentation.model.BootstrapNodeVo
import ru.fredboy.toxoid.utils.countryCodeToEmojiFlag
import javax.inject.Inject

class BootstrapNodeFormatter @Inject constructor() {

    fun format(node: BootstrapNode): BootstrapNodeVo {
        return BootstrapNodeVo(
            socket = "${node.host}:${node.port}",
            flag = countryCodeToEmojiFlag(node.location),
            status = node.status ?: false,
            motd = node.motd.orEmpty(),
        )
    }

}
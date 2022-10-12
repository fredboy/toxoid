package ru.fredboy.toxoid.clean.data.mapper

import ru.fredboy.toxoid.clean.data.model.json.BootstrapNodeDto
import ru.fredboy.toxoid.clean.domain.model.BootstrapNode
import javax.inject.Inject

class BootstrapNodeMapper @Inject constructor() {

    fun map(dto: BootstrapNodeDto): BootstrapNode? {
        return BootstrapNode(
            ipv4 = dto.ipv4 ?: return null,
            ipv6 = dto.ipv6,
            location = dto.location ?: return null,
            status = dto.statusTcp ?: return null,
        )
    }

}
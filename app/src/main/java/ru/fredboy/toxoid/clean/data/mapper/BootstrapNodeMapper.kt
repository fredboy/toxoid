package ru.fredboy.toxoid.clean.data.mapper

import ru.fredboy.toxoid.clean.data.model.json.BootstrapNodeDto
import ru.fredboy.toxoid.clean.data.model.room.BootstrapNodeEntity
import ru.fredboy.toxoid.clean.domain.model.BootstrapNode
import javax.inject.Inject

class BootstrapNodeMapper @Inject constructor(
    private val toxPublicKeyMapper: ToxPublicKeyMapper,
) {

    fun map(dto: BootstrapNodeDto): BootstrapNode {
        return BootstrapNode(
            publicKey = toxPublicKeyMapper.map(requireNotNull(dto.publicKey)),
            host = requireNotNull(dto.ipv4),
            port = requireNotNull(dto.port?.toUShort()),
            location = requireNotNull(dto.location),
            status = dto.statusTcp,
            motd = dto.motd
        )
    }

    fun map(entity: BootstrapNodeEntity): BootstrapNode {
        return BootstrapNode(
            publicKey = toxPublicKeyMapper.map(entity.id),
            host = entity.host,
            port = entity.port.toUShort(),
            location = entity.location,
        )
    }

    fun map(node: BootstrapNode): BootstrapNodeEntity {
        return BootstrapNodeEntity(
            id = node.publicKey.toString(),
            host = node.host,
            port = node.port.toInt(),
            location = node.location,
        )
    }

}
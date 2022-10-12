package ru.fredboy.toxoid.clean.data.source.bootstrap

import ru.fredboy.toxoid.clean.data.model.json.ToxChatNodesDto

interface ToxChatNodesDataSource {

    suspend fun getNodes(): ToxChatNodesDto?

}
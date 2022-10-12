package ru.fredboy.toxoid.clean.data.source.bootstrap

import ru.fredboy.toxoid.clean.data.model.json.ToxChatNodesDto
import ru.fredboy.toxoid.clean.data.source.retrofit.NodesToxChatApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitToxChatNodesDataSource @Inject constructor(
    private val nodesToxChatApi: NodesToxChatApi,
) : ToxChatNodesDataSource {

    override suspend fun getNodes(): ToxChatNodesDto? {
        @Suppress("BlockingMethodInNonBlockingContext")
        return nodesToxChatApi.getToxChatNodes().execute().body()
    }

}
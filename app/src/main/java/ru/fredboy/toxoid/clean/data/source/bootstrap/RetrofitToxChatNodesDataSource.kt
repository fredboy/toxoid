package ru.fredboy.toxoid.clean.data.source.bootstrap

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.create
import ru.fredboy.toxoid.clean.data.model.json.ToxChatNodesDto
import ru.fredboy.toxoid.clean.data.source.retrofit.NodesToxChatApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitToxChatNodesDataSource @Inject constructor() : ToxChatNodesDataSource {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://nodes.tox.chat")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val nodesToxChatApi = retrofit.create<NodesToxChatApi>()

    override suspend fun getNodes(): ToxChatNodesDto? {
        return nodesToxChatApi.getToxChatNodes().execute().body()
    }

}
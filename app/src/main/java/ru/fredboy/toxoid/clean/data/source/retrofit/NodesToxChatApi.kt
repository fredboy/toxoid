package ru.fredboy.toxoid.clean.data.source.retrofit

import retrofit2.Call
import retrofit2.http.GET
import ru.fredboy.toxoid.clean.data.model.json.ToxChatNodesDto

interface NodesToxChatApi {

    @GET("/json")
    fun getToxChatNodes(): Call<ToxChatNodesDto>

}
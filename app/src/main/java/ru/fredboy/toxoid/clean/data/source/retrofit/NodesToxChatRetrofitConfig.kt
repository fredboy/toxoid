package ru.fredboy.toxoid.clean.data.source.retrofit

import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NodesToxChatRetrofitConfig @Inject constructor() : RetrofitConfig {

    override val baseUrl: String = "https://nodes.tox.chat"

    override val converterFactory: Converter.Factory = GsonConverterFactory.create()

}
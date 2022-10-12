package ru.fredboy.toxoid.clean.data.source.retrofit

import retrofit2.Converter

interface RetrofitConfig {
    val baseUrl: String
    val converterFactory: Converter.Factory
}
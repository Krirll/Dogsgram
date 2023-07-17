package ru.krirll.dogsgram.data.network

import okhttp3.OkHttpClient
import retrofit2.Converter.Factory
import retrofit2.Retrofit

object RetrofitBuilder {

    fun create(
        client: OkHttpClient,
        converterFactory: Factory
    ): Api =
        Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
            .create(Api::class.java)
}
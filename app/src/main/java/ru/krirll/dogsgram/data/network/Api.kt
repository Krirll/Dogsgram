package ru.krirll.dogsgram.data.network

import retrofit2.http.GET

interface Api {

    @GET("breeds/image/random/10")
    suspend fun getDogs(): DogDto
}
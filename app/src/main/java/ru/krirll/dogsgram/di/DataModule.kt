package ru.krirll.dogsgram.di

import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import ru.krirll.dogsgram.data.network.Api
import ru.krirll.dogsgram.data.network.RetrofitBuilder
import ru.krirll.dogsgram.data.repository.RepositoryImpl
import ru.krirll.dogsgram.domain.repository.Repository
import java.util.concurrent.TimeUnit

val dataModule = module {

    factory<Converter.Factory> {
        GsonConverterFactory.create()
    }

    factory<OkHttpClient> {
        OkHttpClient.Builder()
            .callTimeout(8, TimeUnit.SECONDS)
            .build()
    }

    single<Api> {
        RetrofitBuilder.create(get(), get())
    }

    single<Repository> {
        RepositoryImpl(
            apiService = get()
        )
    }
}
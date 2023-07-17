package ru.krirll.dogsgram.di

import org.koin.dsl.module
import ru.krirll.dogsgram.domain.use_cases.GetDogsUseCase

val domainModule = module {

    factory {
        GetDogsUseCase(get())
    }
}
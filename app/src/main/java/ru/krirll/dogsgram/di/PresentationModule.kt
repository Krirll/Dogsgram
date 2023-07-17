package ru.krirll.dogsgram.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.krirll.dogsgram.presentation.view_model.MainViewModel

val presentationModule = module {

    viewModel {
        MainViewModel(get())
    }
}
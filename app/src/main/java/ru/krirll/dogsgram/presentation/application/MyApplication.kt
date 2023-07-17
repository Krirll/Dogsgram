package ru.krirll.dogsgram.presentation.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.krirll.dogsgram.di.dataModule
import ru.krirll.dogsgram.di.domainModule
import ru.krirll.dogsgram.di.presentationModule

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(presentationModule, dataModule, domainModule)
        }
    }
}
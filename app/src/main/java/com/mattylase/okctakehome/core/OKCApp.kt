package com.mattylase.okctakehome.core

import android.app.Application
import com.mattylase.okctakehome.core.di.module
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class OKCApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(module)
            androidContext(this@OKCApp)
        }
    }
}
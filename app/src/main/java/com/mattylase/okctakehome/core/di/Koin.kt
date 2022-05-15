package com.mattylase.okctakehome.core.di

import android.util.Log
import com.mattylase.okctakehome.extras.logTag
import com.mattylase.okctakehome.repository.Repository
import com.mattylase.okctakehome.ui.common.TakehomeViewModel
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.annotation.KoinReflectAPI
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Koin is our DI system for the app. Dependencies are configured here and then supplied to the
 * components lazily.
 */
@KoinReflectAPI
val module = module {
    single { Repository() }
    single { Dispatchers.IO }
    httpClient()
    viewModel<TakehomeViewModel>()
}

fun Module.httpClient() = single {
    HttpClient(CIO) {

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d(logTag(), message)
                }
            }
            this.level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            })
        }
    }
}

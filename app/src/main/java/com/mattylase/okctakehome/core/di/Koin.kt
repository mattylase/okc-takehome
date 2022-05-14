package com.mattylase.okctakehome.core.di

import android.util.Log
import com.mattylase.okctakehome.MainActivity
import com.mattylase.okctakehome.extras.LogTag
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

@KoinReflectAPI
val module = module {
    single { Repository() }
    single { Dispatchers.IO }
    httpClient()
    database()

    viewModel<TakehomeViewModel>()

//    single { get<AppDatabase>().newsArticleDao() }
//    single { get<AppDatabase>().searchTopicDao() }
    //single { Cache() }
}

fun Module.httpClient() = single {
    HttpClient(CIO) {

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d(LogTag(), message)
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

fun Module.database() = single {
    //Room.databaseBuilder(get(), AppDatabase::class.java, "demo-db").build()
}
package com.mattylase.okctakehome.repository

import android.util.Log
import com.mattylase.okctakehome.extras.Constants
import com.mattylase.okctakehome.extras.LogTag
import com.mattylase.okctakehome.model.Match
import com.mattylase.okctakehome.model.MatchesResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Repository: KoinComponent {
    private val client by inject<HttpClient>()

    suspend fun fetchMatches(): List<Match> {
        val response = client.get {
            defaultRequestConfig(this)
        }.body<MatchesResponse>()

        return response.data
    }

    private fun defaultRequestConfig(builder: HttpRequestBuilder) = with(builder) {
        url {
            host = Constants.matchesHost
            encodedPath = Constants.matchesPath
        }
    }
}
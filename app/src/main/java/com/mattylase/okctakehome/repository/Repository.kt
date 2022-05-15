package com.mattylase.okctakehome.repository

import com.mattylase.okctakehome.extras.Constants
import com.mattylase.okctakehome.model.Match
import com.mattylase.okctakehome.model.MatchesResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Repository : KoinComponent {
    private val client by inject<HttpClient>()
    private val likedUsers = mutableSetOf<String>()
    private var cachedMatches = listOf<Match>()

    suspend fun fetchMatches(): List<Match> {
        val response = client.get {
            defaultRequestConfig(this)
        }.body<MatchesResponse>()

        cachedMatches = response.data
        cachedMatches.filter { likedUsers.contains(it.userid) }.forEach { it.liked = true }
        return cachedMatches
    }

    suspend fun fetchLikedUsers(): List<Match> {
        if (cachedMatches.isEmpty()) {
            fetchMatches()
        }

        return cachedMatches.filter { likedUsers.contains(it.userid) }
            .sortedByDescending { it.match }
            .take(6)
    }

    fun cachedMatches() = cachedMatches

    fun toggleUserLikeState(id: String) {
        if (likedUsers.contains(id)) {
            cachedMatches.find { it.userid == id }?.liked = false
            likedUsers.remove(id)
        } else {
            cachedMatches.find { it.userid == id }?.liked = true
            likedUsers.add(id)
        }
    }

    private fun defaultRequestConfig(builder: HttpRequestBuilder) = with(builder) {
        url {
            host = Constants.matchesHost
            encodedPath = Constants.matchesPath
        }
    }
}
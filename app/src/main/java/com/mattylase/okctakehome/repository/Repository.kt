package com.mattylase.okctakehome.repository

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.mattylase.okctakehome.extras.Constants
import com.mattylase.okctakehome.extras.logTag
import com.mattylase.okctakehome.repository.model.Match
import com.mattylase.okctakehome.repository.model.MatchesResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * The Repository contains all network access & maintains the app's overall state
 */
class Repository : KoinComponent {
    private val client by inject<HttpClient>()
    private val likedUsers = mutableSetOf<String>()
    private var cachedMatches = listOf<Match>()

    /**
     * Grab the matches from the given api provided for the assignment
     */
    suspend fun fetchMatches(): List<Match>? {
        try {
            val response = client.get { defaultRequestConfig(this) }.body<MatchesResponse>()
            cachedMatches = response.data
            // `likedUsers` would currently be empty without persistence, but this is here to make sure
            // that the UI logic worked as I intended during debugging, and for futureproofing
            cachedMatches.filter { likedUsers.contains(it.userid) }.forEach { it.liked = true }
            return cachedMatches
        } catch (t: Throwable) {
            Log.e(logTag(), "Failed getting the matches!", t)
            return null
        }
    }

    /**
     * For our simple use case, we'll always use the cached value if we have them, so we aren't
     * making undue network requests.
     */
    suspend fun fetchLikedUsers(): List<Match> {
        if (cachedMatches.isEmpty()) {
            fetchMatches()
        }

        return cachedMatches.filter { likedUsers.contains(it.userid) }
            .sortedByDescending { it.match }
            .take(6)
    }

    fun cachedMatches() = cachedMatches

    /**
     * Toggles the "liked" state of a user. In a real app, this might be firing off an API request
     * to send this info to a server. Here we're just maintaining a list locally, which is then
     * cross-referenced as the Repository's other functions are accessed
     */
    fun toggleUserLikeState(id: String) {
        if (likedUsers.contains(id)) {
            cachedMatches.find { it.userid == id }?.liked = false
            likedUsers.remove(id)
        } else {
            cachedMatches.find { it.userid == id }?.liked = true
            likedUsers.add(id)
        }
    }

    @VisibleForTesting
    fun defaultRequestConfig(builder: HttpRequestBuilder) = with(builder) {
        url {
            host = Constants.matchesHost
            encodedPath = Constants.matchesPath
        }
    }
}
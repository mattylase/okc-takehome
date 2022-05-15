package com.mattylase.okctakehome.ui.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mattylase.okctakehome.extras.logTag
import com.mattylase.okctakehome.repository.model.toCandidatesList
import com.mattylase.okctakehome.repository.Repository
import com.mattylase.okctakehome.ui.common.model.CandidateScreenState
import com.mattylase.okctakehome.ui.common.model.UpdateMode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * This viewmodel is shared across all the UI in the app
 */
class TakehomeViewModel(private val dispatcher: CoroutineDispatcher) : ViewModel(), KoinComponent {

    val minLoadingTimeMillis = 1800
    private val repo by inject<Repository>()
    val specialBlendScreenState = MutableStateFlow(CandidateScreenState.default())
    val matchScreenState = MutableStateFlow(CandidateScreenState.default())

    /**
     * Fetches the candidates that populate the "special blend" tab.
     * Added some simple delay
     */
    fun querySpecialBlends() {
        viewModelScope.launch(dispatcher) {
            specialBlendScreenState.update { it.copy(loading = true) }

            val startTime = System.currentTimeMillis()
            val state = repo.fetchMatches()?.let {
                CandidateScreenState(
                    candidates = it.toCandidatesList(),
                    updateMode = UpdateMode.All(),
                    loading = false
                )
            } ?: CandidateScreenState(
                candidates = emptyList(),
                updateMode = UpdateMode.None(),
                networkError = true,
                loading = false
            )
            val diff = System.currentTimeMillis() - startTime
            if (diff < minLoadingTimeMillis) {
                delay(minLoadingTimeMillis - diff)
            }
            specialBlendScreenState.update { state }
        }
    }

    /**
     * Queries for the matches on startup. Under current operation, this should essentially be no-op
     * because our local match list is always generated in-memory after startup. That said, put
     * it here to potentially build upon.
     */
    fun queryMatches() {
        viewModelScope.launch(dispatcher) {
            val matches = repo.fetchLikedUsers()
            matchScreenState.update {
                CandidateScreenState(
                    candidates = matches.toCandidatesList(),
                    updateMode = UpdateMode.All()
                )
            }
        }
    }

    /**
     * When clicked, we update the Repository's understanding of the "liked" users, and then
     * re-propogate a CandidateScreenState that reflects the repository's new understanding of the
     * backing data
     */
    fun onCandidateClick(position: Int, id: String) {
        repo.toggleUserLikeState(id)

        viewModelScope.launch(dispatcher) {
            val liked = repo.fetchLikedUsers()
            matchScreenState.update {
                it.copy(
                    candidates = liked.toCandidatesList(),
                    updateMode = UpdateMode.All()
                )
            }

            specialBlendScreenState.update {
                it.copy(
                    candidates = repo.cachedMatches().toCandidatesList(),
                    updateMode = UpdateMode.Single(position)
                )
            }
        }
    }
}
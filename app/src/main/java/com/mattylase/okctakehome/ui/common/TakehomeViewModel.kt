package com.mattylase.okctakehome.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mattylase.okctakehome.model.toCandidatesList
import com.mattylase.okctakehome.repository.Repository
import com.mattylase.okctakehome.ui.common.model.CandidateScreenState
import com.mattylase.okctakehome.ui.common.model.UpdateMode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TakehomeViewModel(val dispatcher: CoroutineDispatcher) : ViewModel(), KoinComponent {

    private val repo by inject<Repository>()
    val specialBlendScreenState = MutableStateFlow(CandidateScreenState.default())
    val matchScreenState = MutableStateFlow(CandidateScreenState.default())

    fun listenForSpecialBlendUpdates() {
        viewModelScope.launch(dispatcher) {
            val matches = repo.fetchMatches()
            specialBlendScreenState.update {
                it.copy(
                    candidates = matches.toCandidatesList(),
                    updateMode = UpdateMode.All()
                )
            }
        }
    }

    fun listenForMatchUpdates() {

    }

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
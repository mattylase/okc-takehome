package com.mattylase.okctakehome.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mattylase.okctakehome.model.toCandidatesList
import com.mattylase.okctakehome.repository.Repository
import com.mattylase.okctakehome.ui.common.model.CandidateScreenState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TakehomeViewModel(val dispatcher: CoroutineDispatcher): ViewModel(), KoinComponent {

    private val repo by inject<Repository>()
    val specialBlendScreenState = MutableStateFlow(CandidateScreenState.default())
    val matchScreenState = MutableStateFlow(CandidateScreenState.default())

    fun listenForSpecialBlendUpdates() {
        viewModelScope.launch(dispatcher) {
            val matches = repo.fetchMatches()
            specialBlendScreenState.update { it.copy(candidates = matches.toCandidatesList()) }
        }
    }

    fun listenForMatchUpdates() {

    }
}
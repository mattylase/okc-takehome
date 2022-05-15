package com.mattylase.okctakehome.ui.main

import androidx.lifecycle.lifecycleScope
import com.mattylase.okctakehome.ui.common.CandidateFragment
import com.mattylase.okctakehome.ui.common.model.UpdateMode
import kotlinx.coroutines.launch

class MatchFragment: CandidateFragment() {

    override fun listenForUpdates() {
        viewModel.listenForMatchUpdates()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.matchScreenState.collect {
                candidateAdapter.updateAllCandidates(it.candidates)
            }
        }
    }
}

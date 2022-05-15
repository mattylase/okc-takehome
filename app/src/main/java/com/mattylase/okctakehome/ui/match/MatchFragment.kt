package com.mattylase.okctakehome.ui.match

import androidx.lifecycle.lifecycleScope
import com.mattylase.okctakehome.ui.common.CandidateFragment
import kotlinx.coroutines.launch

class MatchFragment: CandidateFragment() {

    override fun listenForUpdates() {
        viewModel.queryMatches()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.matchScreenState.collect {
                candidateAdapter.updateAllCandidates(it.candidates)
            }
        }
    }
}

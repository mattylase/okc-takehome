package com.mattylase.okctakehome.ui.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.mattylase.okctakehome.ui.common.CandidateFragment
import com.mattylase.okctakehome.ui.common.model.UpdateMode
import kotlinx.coroutines.launch

class SpecialBlendFragment : CandidateFragment() {

    override fun listenForUpdates() {
        viewModel.listenForSpecialBlendUpdates()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.specialBlendScreenState.collect {
                when (it.updateMode) {
                    is UpdateMode.All -> candidateAdapter.updateAllCandidates(it.candidates)
                    is UpdateMode.Single -> candidateAdapter.updateCandidate(
                        it.updateMode.position,
                        it.candidates[it.updateMode.position]
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        candidateAdapter.setOnClickListener(::onCandidateClick)
    }

    private fun onCandidateClick(position: Int, id: String) {
        viewModel.onCandidateClick(position, id)
    }
}
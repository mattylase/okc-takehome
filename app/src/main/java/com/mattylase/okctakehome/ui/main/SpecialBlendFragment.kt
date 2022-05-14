package com.mattylase.okctakehome.ui.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.mattylase.okctakehome.ui.common.CandidateFragment
import kotlinx.coroutines.launch

class SpecialBlendFragment : CandidateFragment() {

    override fun listenForUpdates() {
        viewModel.listenForSpecialBlendUpdates()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.specialBlendScreenState.collect {
                candidateAdapter.updateCandidates(it.candidates)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenForUpdates()
    }
}
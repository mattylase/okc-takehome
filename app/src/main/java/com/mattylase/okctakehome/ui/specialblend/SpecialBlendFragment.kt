package com.mattylase.okctakehome.ui.specialblend

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.mattylase.okctakehome.R
import com.mattylase.okctakehome.ui.common.CandidateFragment
import com.mattylase.okctakehome.ui.common.model.CandidateScreenState
import com.mattylase.okctakehome.ui.common.model.UpdateMode
import kotlinx.coroutines.launch

class SpecialBlendFragment : CandidateFragment() {

    override fun listenForUpdates() {
        viewModel.querySpecialBlends()

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.specialBlendScreenState.collect {
                when (it.updateMode) {
                    is UpdateMode.All -> {
                        candidateAdapter.updateAllCandidates(it.candidates)
                    }
                    is UpdateMode.Single -> {
                        candidateAdapter.updateCandidate(
                            it.updateMode.position,
                            it.candidates[it.updateMode.position]
                        )
                    }
                    // is UpdateMode.None -> no op for now
                }

                when {
                    it.loading -> showLoadingView()
                    it.networkError -> {
                        Toast.makeText(context, R.string.toast_fetch_error, Toast.LENGTH_LONG).show()
                        showErrorView()
                    }
                    else -> showRecyclerView()
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
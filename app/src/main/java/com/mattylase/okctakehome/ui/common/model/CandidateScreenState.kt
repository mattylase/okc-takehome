package com.mattylase.okctakehome.ui.common.model

data class CandidateScreenState(
    val candidates: List<Candidate>
) {
    companion object {
        fun default(): CandidateScreenState {
            return CandidateScreenState(
                candidates = emptyList()
            )
        }
    }
}

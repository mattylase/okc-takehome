package com.mattylase.okctakehome.ui.common.model

data class CandidateScreenState(
    val candidates: List<Candidate>,
    val updateMode: UpdateMode
) {
    companion object {
        fun default(): CandidateScreenState {
            return CandidateScreenState(
                candidates = emptyList(),
                updateMode = UpdateMode.None()
            )
        }
    }
}

sealed class UpdateMode {
    class All: UpdateMode()
    class Single(val position: Int): UpdateMode()
    class None: UpdateMode()
}

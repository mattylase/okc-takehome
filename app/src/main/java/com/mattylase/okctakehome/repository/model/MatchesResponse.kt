package com.mattylase.okctakehome.repository.model

import kotlinx.serialization.Serializable

@Serializable
data class MatchesResponse(
    val data: List<Match>,
    val paging: Paging,
    val total_matches: Int
)
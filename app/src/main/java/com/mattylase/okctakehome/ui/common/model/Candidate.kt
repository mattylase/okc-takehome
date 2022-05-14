package com.mattylase.okctakehome.ui.common.model

data class Candidate(
    val username: String,
    val age: String,
    val city: String,
    val state: String,
    val matchPercentage: String,
    val thumbUrl: String,
    val liked: Boolean
)

fun Candidate.default(): Candidate {
    return Candidate(
        username = "",
        age = "",
        city = "",
        state = "",
        matchPercentage = "",
        thumbUrl = "",
        liked = false
    )
}
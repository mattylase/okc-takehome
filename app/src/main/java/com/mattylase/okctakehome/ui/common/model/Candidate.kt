package com.mattylase.okctakehome.ui.common.model

data class Candidate(
    val id: String,
    val username: String,
    val age: String,
    val city: String,
    val state: String,
    val matchPercentage: String,
    val thumbUrl: String,
    val liked: Boolean
)

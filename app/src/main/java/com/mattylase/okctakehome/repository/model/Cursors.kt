package com.mattylase.okctakehome.repository.model

import kotlinx.serialization.Serializable

@Serializable
data class Cursors(
    val after: String,
    val before: String,
    val current: String
)

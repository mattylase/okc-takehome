package com.mattylase.okctakehome.model

import kotlinx.serialization.Serializable

@Serializable
data class Cursors(
    val after: String,
    val before: String,
    val current: String
)

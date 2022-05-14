package com.mattylase.okctakehome.model

import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    val large: String?,
    val medium: String?,
    val original: String?,
    val small: String?
)
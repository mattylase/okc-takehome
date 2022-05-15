package com.mattylase.okctakehome.repository.model

import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    val large: String?,
    val medium: String?,
    val original: String?,
    val small: String?
)
package com.mattylase.okctakehome.repository.model

import kotlinx.serialization.Serializable

@Serializable
data class Paging(
    val cursors: Cursors
)
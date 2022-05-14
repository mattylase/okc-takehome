package com.mattylase.okctakehome.model

import kotlinx.serialization.Serializable

@Serializable
data class Paging(
    val cursors: Cursors
)
package com.mattylase.okctakehome.model

import kotlinx.serialization.Serializable

@Serializable
data class Location (
	val city_name: String?,
	val country_code: String?,
	val country_name: String?,
	val state_code: String?,
	val state_name: String?
)
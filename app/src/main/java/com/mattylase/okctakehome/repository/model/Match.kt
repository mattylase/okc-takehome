package com.mattylase.okctakehome.repository.model

import com.mattylase.okctakehome.ui.common.model.Candidate
import kotlinx.serialization.Serializable

@Serializable
data class Match(
    val age: Int?,
    val is_online: Int?,
    var liked: Boolean?,
    val location: Location?,
    val match: Int?,
    val photo: Photo?,
    val userid: String,
    val username: String?
)

fun Collection<Match>.toCandidatesList(): List<Candidate> {
    return map {
        Candidate(
            id = it.userid,
            username = it.username ?: "",
            age = it.age?.toString() ?: "",
            city = it.location?.city_name ?: "",
            state = it.location?.state_code ?: "",
            matchPercentage = it.match?.toString() ?: "",
            thumbUrl = it.photo?.small ?: "",
            liked = it.liked ?: false
        )
    }
}
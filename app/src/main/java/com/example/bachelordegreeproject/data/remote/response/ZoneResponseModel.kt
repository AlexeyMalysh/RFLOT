package com.example.bachelordegreeproject.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ZoneResponseModel(
    val idZone: String,
    val name: String,
    val fullNameChecker: List<String>
)

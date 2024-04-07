package com.example.bachelordegreeproject.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class StartSessionRequestModel(
    val idPlane: String,
    val typeCheck: String,
    val fullNameChecker: String
)

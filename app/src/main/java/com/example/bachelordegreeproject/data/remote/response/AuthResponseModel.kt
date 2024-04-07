package com.example.bachelordegreeproject.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseModel(
    val status: String,
    val message: String
)

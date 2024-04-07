package com.example.bachelordegreeproject.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequestModel(
    val login: String,
    val pass: String?
)

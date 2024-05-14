package com.example.bachelordegreeproject.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class AuthByRfidRequestModel(
    val rfid: String
)

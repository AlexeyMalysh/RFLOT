package com.example.bachelordegreeproject.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class ConnectToHubRequestModel(
    val idReport: String,
    val idZone: String,
    val idUser: String
)

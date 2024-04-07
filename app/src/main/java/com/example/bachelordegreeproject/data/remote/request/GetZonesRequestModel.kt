package com.example.bachelordegreeproject.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class GetZonesRequestModel(
    val idReport: String
)

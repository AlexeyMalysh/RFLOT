package com.example.bachelordegreeproject.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class EquipStateResponseModel(
    val space: String,
    val status: Int
)

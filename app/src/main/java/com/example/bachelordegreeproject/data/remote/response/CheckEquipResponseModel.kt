package com.example.bachelordegreeproject.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class CheckEquipResponseModel(
    val name: String,
    val space: String,
    val type: String,
    val lastStatus: String
)

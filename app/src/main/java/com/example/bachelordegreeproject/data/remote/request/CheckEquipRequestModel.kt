package com.example.bachelordegreeproject.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class CheckEquipRequestModel(
    val RfId: String,
    val idReport: String
)

package com.example.bachelordegreeproject.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class CheckZoneResponseModel(
    val name: String,
    val equipResults: List<EquipStateResponseModel>,
    val spaces: List<String>
)

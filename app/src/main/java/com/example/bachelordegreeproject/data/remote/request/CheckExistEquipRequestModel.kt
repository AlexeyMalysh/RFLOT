package com.example.bachelordegreeproject.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class CheckExistEquipRequestModel(
    val idEquip: String
)

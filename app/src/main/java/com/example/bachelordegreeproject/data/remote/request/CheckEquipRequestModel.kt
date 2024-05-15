package com.example.bachelordegreeproject.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class CheckEquipRequestModel(
    val idEquip: String,
    val statusEquip: Int,
    val idReport: String,
    val idZone: String,
    val idUser: String
)

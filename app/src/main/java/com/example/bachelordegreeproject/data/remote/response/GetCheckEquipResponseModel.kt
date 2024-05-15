package com.example.bachelordegreeproject.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class GetCheckEquipResponseModel(
    val equipInfo: CheckEquipResponseModel
)

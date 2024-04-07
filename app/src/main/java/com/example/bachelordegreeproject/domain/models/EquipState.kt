package com.example.bachelordegreeproject.domain.models

import com.example.bachelordegreeproject.core.util.constants.EquipType

data class EquipState(
    val space: String,
    val status: EquipType
)

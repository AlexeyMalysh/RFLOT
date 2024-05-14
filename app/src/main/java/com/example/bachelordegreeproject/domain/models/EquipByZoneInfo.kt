package com.example.bachelordegreeproject.domain.models

data class EquipByZoneInfo(
    val zoneName: String,
    val equip: List<EquipState>,
    val spaces: List<String>
)

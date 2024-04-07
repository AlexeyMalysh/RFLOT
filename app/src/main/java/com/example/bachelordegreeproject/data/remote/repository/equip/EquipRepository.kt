package com.example.bachelordegreeproject.data.remote.repository.equip

import com.example.bachelordegreeproject.domain.models.EquipByZoneInfo
import com.example.bachelordegreeproject.domain.models.Zones

interface EquipRepository {
    suspend fun checkEquip(): Result<Zones>
}

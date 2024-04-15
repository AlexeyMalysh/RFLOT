package com.example.bachelordegreeproject.data.remote.repository.equip

import com.example.bachelordegreeproject.domain.models.Zones
import com.example.bachelordegreeproject.core.util.constants.Result

interface EquipRepository {
    suspend fun checkEquip(): Result<Zones>
}

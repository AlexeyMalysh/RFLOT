package com.example.bachelordegreeproject.data.remote.repository.zones

import com.example.bachelordegreeproject.domain.models.EquipByZoneInfo
import com.example.bachelordegreeproject.domain.models.Zones

interface ZonesRepository {
    suspend fun getAllZones(): Result<Zones>

    suspend fun getZonesInfo(zoneName: String): Result<EquipByZoneInfo>
}

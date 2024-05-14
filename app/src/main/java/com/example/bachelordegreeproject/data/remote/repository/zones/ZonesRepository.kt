package com.example.bachelordegreeproject.data.remote.repository.zones

import com.example.bachelordegreeproject.domain.models.EquipByZoneInfo
import com.example.bachelordegreeproject.domain.models.Zones
import com.example.bachelordegreeproject.core.util.constants.Result
import com.example.bachelordegreeproject.data.remote.request.CheckZoneRequestModel

interface ZonesRepository {
    suspend fun getAllZones(): Result<Zones>
    suspend fun startCheckZone(zoneId: String): Result<EquipByZoneInfo>
    suspend fun connectToHub(zoneId: String): Result<Unit>

    suspend fun connectToHub(params: CheckZoneRequestModel): Result<Unit>
}

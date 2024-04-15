package com.example.bachelordegreeproject.data.remote.repository.zones

import com.example.bachelordegreeproject.core.network.RflotHttpService
import com.example.bachelordegreeproject.data.local.repository.SessionRepository
import com.example.bachelordegreeproject.data.remote.mappers.EquipByZoneMapper
import com.example.bachelordegreeproject.data.remote.mappers.ZonesMapper
import com.example.bachelordegreeproject.data.remote.request.CheckZoneRequestModel
import com.example.bachelordegreeproject.data.remote.request.GetZonesRequestModel
import com.example.bachelordegreeproject.domain.models.EquipByZoneInfo
import com.example.bachelordegreeproject.domain.models.Zones
import javax.inject.Inject
import com.example.bachelordegreeproject.core.util.constants.Result

class ZonesRepositoryImpl @Inject constructor(
    private val rflotHttpService: RflotHttpService,
    private val zonesMapper: ZonesMapper,
    private val equipByZoneMapper: EquipByZoneMapper,
    private val sessionRepository: SessionRepository
) : ZonesRepository {
    override suspend fun getAllZones(): Result<Zones> {
        val reportId = sessionRepository.getSession().reportId
        val params = GetZonesRequestModel(idReport = reportId)
        return rflotHttpService.getZones(params).map { zonesMapper.map(it) }
    }

    override suspend fun getZonesInfo(zoneName: String): Result<EquipByZoneInfo> {
        val reportId = sessionRepository.getSession().reportId
        val params = CheckZoneRequestModel(idReport = zoneName, nameZone = reportId)
        return rflotHttpService.checkZone(params).map { equipByZoneMapper.map(it) }
    }
}

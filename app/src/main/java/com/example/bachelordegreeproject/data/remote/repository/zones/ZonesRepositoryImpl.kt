package com.example.bachelordegreeproject.data.remote.repository.zones

import com.example.bachelordegreeproject.core.network.ktor.RflotHttpService
import com.example.bachelordegreeproject.data.local.repository.SessionRepository
import com.example.bachelordegreeproject.data.remote.mappers.EquipByZoneMapper
import com.example.bachelordegreeproject.data.remote.mappers.ZonesMapper
import com.example.bachelordegreeproject.data.remote.request.CheckZoneRequestModel
import com.example.bachelordegreeproject.data.remote.request.GetZonesRequestModel
import com.example.bachelordegreeproject.domain.models.EquipByZoneInfo
import com.example.bachelordegreeproject.domain.models.Zones
import javax.inject.Inject
import com.example.bachelordegreeproject.core.util.constants.Result
import com.example.bachelordegreeproject.data.remote.mappers.ZoneToConnectHubMapper
import com.example.bachelordegreeproject.data.remote.request.ConnectToHubRequestModel

class ZonesRepositoryImpl @Inject constructor(
    private val rflotHttpService: RflotHttpService,
    private val zonesMapper: ZonesMapper,
    private val equipByZoneMapper: EquipByZoneMapper,
    private val zoneToConnectHubMapper: ZoneToConnectHubMapper,
    private val sessionRepository: SessionRepository
) : ZonesRepository {
    override suspend fun getAllZones(): Result<Zones> {
        val reportId = sessionRepository.getSession()?.reportId ?: return Result.Fail()
        val params = GetZonesRequestModel(idReport = reportId)
        return rflotHttpService.getZones(params).map { zonesMapper.map(it) }
    }
    override suspend fun startCheckZone(zoneId: String): Result<EquipByZoneInfo> {
        val reportId = sessionRepository.getSession()?.reportId ?: return Result.Fail()
        val userId = sessionRepository.getSession()?.userId ?: return Result.Fail()
        val params = CheckZoneRequestModel(idReport = reportId, idUser = userId, idZone = zoneId)
        val result = rflotHttpService.checkZone(params).map { equipByZoneMapper.map(it) }
        if (result is Result.Success) connectToHub(params)
        return result
    }
    override suspend fun connectToHub(zoneId: String): Result<Unit> {
        val reportId = sessionRepository.getSession()?.reportId ?: return Result.Fail()
        val userId = sessionRepository.getSession()?.userId ?: return Result.Fail()
        val params = ConnectToHubRequestModel(idReport = reportId, idUser = userId, idZone = zoneId)
        return rflotHttpService.connectToHub(params)
    }
    override suspend fun connectToHub(params: CheckZoneRequestModel): Result<Unit> =
        rflotHttpService.connectToHub(zoneToConnectHubMapper.map(params))
}

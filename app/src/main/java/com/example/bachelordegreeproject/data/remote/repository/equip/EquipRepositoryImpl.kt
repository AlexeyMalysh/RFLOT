package com.example.bachelordegreeproject.data.remote.repository.equip

import com.example.bachelordegreeproject.core.network.ktor.RflotHttpService
import com.example.bachelordegreeproject.core.util.constants.Result
import com.example.bachelordegreeproject.data.local.repository.SessionRepository
import com.example.bachelordegreeproject.data.remote.mappers.EquipInfoMapper
import com.example.bachelordegreeproject.data.remote.request.CheckEquipRequestModel
import com.example.bachelordegreeproject.data.remote.request.CheckExistEquipRequestModel
import com.example.bachelordegreeproject.domain.models.EquipInfo
import javax.inject.Inject

class EquipRepositoryImpl @Inject constructor(
    private val rflotHttpService: RflotHttpService,
    private val equipInfoMapper: EquipInfoMapper,
    private val sessionRepository: SessionRepository
) : EquipRepository {
    override suspend fun checkEquip(rfid: String, status: Int): Result<EquipInfo> {
        val reportId = sessionRepository.getSession()?.reportId ?: return Result.Fail()
        val userId = sessionRepository.getSession()?.userId ?: return Result.Fail()
        val zoneId = sessionRepository.getSession()?.zoneId ?: return Result.Fail()
        val params = CheckEquipRequestModel(
            idEquip = rfid,
            statusEquip = status,
            idReport = reportId,
            idUser = userId,
            idZone = zoneId
        )
        return rflotHttpService.checkEquip(params).map { equipInfoMapper.map(it) }
    }

    override suspend fun checkExistEquip(rfid: String): Result<EquipInfo> {
        return rflotHttpService.checkExistEquip(CheckExistEquipRequestModel(rfid))
            .map { equipInfoMapper.map(it.equipInfo) }
    }
}

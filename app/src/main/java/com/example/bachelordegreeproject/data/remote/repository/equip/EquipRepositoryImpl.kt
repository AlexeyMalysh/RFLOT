package com.example.bachelordegreeproject.data.remote.repository.equip

import com.example.bachelordegreeproject.core.network.RflotHttpService
import com.example.bachelordegreeproject.core.util.constants.Result
import com.example.bachelordegreeproject.data.local.repository.SessionRepository
import com.example.bachelordegreeproject.data.remote.mappers.EquipInfoMapper
import com.example.bachelordegreeproject.data.remote.request.CheckEquipRequestModel
import com.example.bachelordegreeproject.domain.models.EquipInfo
import javax.inject.Inject

class EquipRepositoryImpl @Inject constructor(
    private val rflotHttpService: RflotHttpService,
    private val equipInfoMapper: EquipInfoMapper,
    private val sessionRepository: SessionRepository
) : EquipRepository {
    override suspend fun checkEquip(rfid: String): Result<EquipInfo> {
        val reportId = sessionRepository.getSession()?.reportId ?: ""
        val params = CheckEquipRequestModel(reportId, rfid)
        return rflotHttpService.checkEquip(params).map { equipInfoMapper.map(it) }
    }
}

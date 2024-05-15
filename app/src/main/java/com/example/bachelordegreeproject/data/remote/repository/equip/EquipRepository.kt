package com.example.bachelordegreeproject.data.remote.repository.equip

import com.example.bachelordegreeproject.core.util.constants.Result
import com.example.bachelordegreeproject.domain.models.EquipInfo

interface EquipRepository {
    suspend fun checkEquip(rfid: String, status: Int): Result<EquipInfo>
    suspend fun checkExistEquip(rfid: String): Result<EquipInfo>
}

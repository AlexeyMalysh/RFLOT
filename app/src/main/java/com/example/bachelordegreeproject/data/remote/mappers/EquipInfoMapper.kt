package com.example.bachelordegreeproject.data.remote.mappers

import com.example.bachelordegreeproject.core.util.Mapper
import com.example.bachelordegreeproject.data.remote.response.CheckEquipResponseModel
import com.example.bachelordegreeproject.domain.models.EquipInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EquipInfoMapper @Inject constructor() : Mapper<CheckEquipResponseModel, EquipInfo> {
    override fun map(input: CheckEquipResponseModel): EquipInfo = EquipInfo(
        name = input.name,
        space = input.space,
        type = input.type,
        lastStatus = input.lastStatus
    )
}

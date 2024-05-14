package com.example.bachelordegreeproject.data.remote.mappers

import com.example.bachelordegreeproject.core.util.Mapper
import com.example.bachelordegreeproject.data.remote.response.CheckZoneResponseModel
import com.example.bachelordegreeproject.domain.models.EquipByZoneInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EquipByZoneMapper @Inject constructor(private val equipStateMapper: EquipStateMapper) :
    Mapper<CheckZoneResponseModel, EquipByZoneInfo> {
    override fun map(input: CheckZoneResponseModel): EquipByZoneInfo = EquipByZoneInfo(
        zoneName = input.name,
        equip = input.equipResults.map(equipStateMapper::map),
        spaces = input.spaces
    )
}

package com.example.bachelordegreeproject.data.remote.mappers

import com.example.bachelordegreeproject.core.util.Mapper
import com.example.bachelordegreeproject.core.util.constants.EquipType
import com.example.bachelordegreeproject.data.remote.response.EquipStateResponseModel
import com.example.bachelordegreeproject.domain.models.EquipState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EquipStateMapper @Inject constructor() : Mapper<EquipStateResponseModel, EquipState> {
    override fun map(input: EquipStateResponseModel): EquipState = EquipState(
        space = input.space,
        status = EquipType.findByValue(input.status)
    )
}

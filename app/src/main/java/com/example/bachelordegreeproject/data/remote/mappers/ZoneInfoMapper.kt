package com.example.bachelordegreeproject.data.remote.mappers

import com.example.bachelordegreeproject.core.util.Mapper
import com.example.bachelordegreeproject.data.remote.response.ZoneResponseModel
import com.example.bachelordegreeproject.domain.models.ZoneInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ZoneInfoMapper @Inject constructor() : Mapper<ZoneResponseModel, ZoneInfo> {
    override fun map(input: ZoneResponseModel): ZoneInfo = ZoneInfo(
        idZone = input.idZone,
        name = input.name,
        reviewersName = input.fullNameChecker
    )
}

package com.example.bachelordegreeproject.data.remote.mappers

import com.example.bachelordegreeproject.core.util.Mapper
import com.example.bachelordegreeproject.data.remote.response.GetZonesResponseModel
import com.example.bachelordegreeproject.domain.models.Zones
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ZonesMapper @Inject constructor(
    private val zoneInfoMapper: ZoneInfoMapper
) : Mapper<GetZonesResponseModel, Zones> {
    override fun map(input: GetZonesResponseModel): Zones = Zones(
        zonesInfo = input.zonesInfo.map(zoneInfoMapper::map)
    )
}

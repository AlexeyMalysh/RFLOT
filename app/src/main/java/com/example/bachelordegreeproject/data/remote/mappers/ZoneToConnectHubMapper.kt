package com.example.bachelordegreeproject.data.remote.mappers

import com.example.bachelordegreeproject.core.util.Mapper
import com.example.bachelordegreeproject.data.remote.request.CheckZoneRequestModel
import com.example.bachelordegreeproject.data.remote.request.ConnectToHubRequestModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ZoneToConnectHubMapper @Inject constructor() : Mapper<CheckZoneRequestModel, ConnectToHubRequestModel> {
    override fun map(input: CheckZoneRequestModel): ConnectToHubRequestModel = ConnectToHubRequestModel(
        idZone = input.idZone,
        idReport = input.idReport,
        idUser = input.idUser
    )
}

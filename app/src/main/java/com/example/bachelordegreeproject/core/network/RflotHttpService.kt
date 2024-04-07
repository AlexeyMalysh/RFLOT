package com.example.bachelordegreeproject.core.network

import com.example.bachelordegreeproject.data.remote.request.AuthRequestModel
import com.example.bachelordegreeproject.data.remote.request.CheckEquipRequestModel
import com.example.bachelordegreeproject.data.remote.request.CheckZoneRequestModel
import com.example.bachelordegreeproject.data.remote.request.GetZonesRequestModel
import com.example.bachelordegreeproject.data.remote.request.StartSessionRequestModel
import com.example.bachelordegreeproject.data.remote.response.AuthResponseModel
import com.example.bachelordegreeproject.data.remote.response.CheckEquipResponseModel
import com.example.bachelordegreeproject.data.remote.response.CheckZoneResponseModel
import com.example.bachelordegreeproject.data.remote.response.GetZonesResponseModel
import com.example.bachelordegreeproject.data.remote.response.SessionResponseModel

interface RflotHttpService {

    suspend fun auth(authParams: AuthRequestModel): Result<AuthResponseModel>

    suspend fun startSession(params: StartSessionRequestModel): Result<SessionResponseModel>

    suspend fun getZones(params: GetZonesRequestModel): Result<GetZonesResponseModel>

    suspend fun checkZone(params: CheckZoneRequestModel): Result<CheckZoneResponseModel>

    suspend fun checkEquip(params: CheckEquipRequestModel): Result<CheckEquipResponseModel>
}

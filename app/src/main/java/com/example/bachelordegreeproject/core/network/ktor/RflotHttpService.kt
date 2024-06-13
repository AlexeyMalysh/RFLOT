package com.example.bachelordegreeproject.core.network.ktor

import com.example.bachelordegreeproject.core.util.constants.Result
import com.example.bachelordegreeproject.data.remote.request.AuthByLoginRequestModel
import com.example.bachelordegreeproject.data.remote.request.AuthByRfidRequestModel
import com.example.bachelordegreeproject.data.remote.request.CheckEquipRequestModel
import com.example.bachelordegreeproject.data.remote.request.CheckExistEquipRequestModel
import com.example.bachelordegreeproject.data.remote.request.CheckZoneRequestModel
import com.example.bachelordegreeproject.data.remote.request.ConnectToHubRequestModel
import com.example.bachelordegreeproject.data.remote.request.GetZonesRequestModel
import com.example.bachelordegreeproject.data.remote.request.StartSessionRequestModel
import com.example.bachelordegreeproject.data.remote.response.AuthPlaneResponseModel
import com.example.bachelordegreeproject.data.remote.response.AuthResponseModel
import com.example.bachelordegreeproject.data.remote.response.CheckEquipResponseModel
import com.example.bachelordegreeproject.data.remote.response.GetCheckEquipResponseModel
import com.example.bachelordegreeproject.data.remote.response.GetZonesResponseModel
import com.example.bachelordegreeproject.data.remote.response.StartCheckZoneResponseModel

interface RflotHttpService {
    suspend fun authByLogin(authParams: AuthByLoginRequestModel): Result<AuthResponseModel>
    suspend fun authByRfid(authParams: AuthByRfidRequestModel): Result<AuthResponseModel>
    suspend fun startSession(params: StartSessionRequestModel): Result<AuthPlaneResponseModel>
    suspend fun getZones(params: GetZonesRequestModel): Result<GetZonesResponseModel>
    suspend fun checkZone(params: CheckZoneRequestModel): Result<StartCheckZoneResponseModel>
    suspend fun checkEquip(params: CheckEquipRequestModel): Result<CheckEquipResponseModel>
    suspend fun checkExistEquip(params: CheckExistEquipRequestModel): Result<GetCheckEquipResponseModel>
    suspend fun closeSession(params: StartSessionRequestModel): Result<Unit>
    suspend fun connectToHub(params: ConnectToHubRequestModel): Result<Unit>
}

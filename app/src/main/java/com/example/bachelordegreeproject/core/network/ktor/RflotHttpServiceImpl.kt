package com.example.bachelordegreeproject.core.network.ktor

import com.example.bachelordegreeproject.core.util.constants.RflotUrl
import com.example.bachelordegreeproject.data.remote.request.AuthByLoginRequestModel
import com.example.bachelordegreeproject.data.remote.request.CheckEquipRequestModel
import com.example.bachelordegreeproject.data.remote.request.CheckZoneRequestModel
import com.example.bachelordegreeproject.data.remote.request.GetZonesRequestModel
import com.example.bachelordegreeproject.data.remote.request.StartSessionRequestModel
import com.example.bachelordegreeproject.data.remote.response.AuthResponseModel
import com.example.bachelordegreeproject.data.remote.response.CheckEquipResponseModel
import com.example.bachelordegreeproject.data.remote.response.CheckZoneResponseModel
import com.example.bachelordegreeproject.data.remote.response.GetZonesResponseModel
import com.example.bachelordegreeproject.data.remote.response.AuthPlaneResponseModel
import com.example.bachelordegreeproject.core.util.constants.Result
import com.example.bachelordegreeproject.data.remote.request.AuthByRfidRequestModel
import com.example.bachelordegreeproject.data.remote.request.ConnectToHubRequestModel
import com.example.bachelordegreeproject.di.IoDispatcher
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class RflotHttpServiceImpl @Inject constructor(
    private val httpClient: RflotHttpClient,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : RflotHttpService {

    override suspend fun authByLogin(authParams: AuthByLoginRequestModel): Result<AuthResponseModel> =
        withContext(coroutineDispatcher) {
            return@withContext try {
                Result.Success(
                    httpClient().post {
                        url(path = RflotUrl.authByLogin)
                        setBody(authParams)
                    }.body()
                )
            } catch (e: Exception) {
                Timber.e("Failed to auth: $e")
                Result.Fail(e)
            }
        }

    override suspend fun authByRfid(authParams: AuthByRfidRequestModel): Result<AuthResponseModel> =
        withContext(coroutineDispatcher) {
            return@withContext try {
                Result.Success(
                    httpClient().post {
                        url(path = RflotUrl.authByRfid)
                        setBody(authParams)
                    }.body()
                )
            } catch (e: Exception) {
                Timber.e("Failed to auth: $e")
                Result.Fail(e)
            }
        }

    override suspend fun startSession(params: StartSessionRequestModel): Result<AuthPlaneResponseModel> =
        withContext(coroutineDispatcher) {
            return@withContext try {
                Result.Success(
                    httpClient().post {
                        url(path = RflotUrl.authPlane)
                        setBody(params)
                    }.body()
                )
            } catch (e: Exception) {
                Timber.e("Failed to start session: $e")
                Result.Fail(e)
            }
        }

    override suspend fun getZones(params: GetZonesRequestModel): Result<GetZonesResponseModel> =
        withContext(coroutineDispatcher) {
            return@withContext try {
                Result.Success(
                    httpClient().post {
                        url(path = RflotUrl.getZones)
                        setBody(params)
                    }.body()
                )
            } catch (e: Exception) {
                Timber.e("Failed to get zones: $e")
                Result.Fail(e)
            }
        }

    override suspend fun checkZone(params: CheckZoneRequestModel): Result<CheckZoneResponseModel> =
        withContext(coroutineDispatcher) {
            return@withContext try {
                Result.Success(
                    httpClient().post {
                        url(path = RflotUrl.checkZone)
                        setBody(params)
                    }.body()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                Timber.e("Failed to check zone: $e")
                Result.Fail(e)
            }
        }

    override suspend fun checkEquip(params: CheckEquipRequestModel): Result<CheckEquipResponseModel> =
        withContext(coroutineDispatcher) {
            return@withContext try {
                Result.Success(
                    httpClient().post {
                        url(path = RflotUrl.checkEquip)
                        setBody(params)
                    }.body()
                )
            } catch (e: Exception) {
                Timber.e("Failed to check equip: $e")
                Result.Fail(e)
            }
        }

    // TODO
    override suspend fun closeSession(params: StartSessionRequestModel): Result<Unit> =
        withContext(coroutineDispatcher) {
            return@withContext try {
                Result.Success(
                    httpClient().post {
                        url(path = RflotUrl.closeZone)
                        setBody(params)
                    }.body()
                )
            } catch (e: Exception) {
                Timber.e("Failed to start session: $e")
                Result.Fail(e)
            }
    }

    override suspend fun connectToHub(params: ConnectToHubRequestModel): Result<Unit> =
        withContext(coroutineDispatcher) {
            return@withContext try {
                Result.Success(
                    httpClient().post {
                        url(path = RflotUrl.socketPath)
                        setBody(params)
                    }.body()
                )
            } catch (e: Exception) {
                Timber.e("Failed to connect to hub: $e")
                Result.Fail(e)
            }
        }
}

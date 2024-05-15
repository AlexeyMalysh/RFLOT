package com.example.bachelordegreeproject.data.remote.repository.auth

import com.example.bachelordegreeproject.core.network.ktor.RflotHttpService
import com.example.bachelordegreeproject.core.util.constants.Result
import com.example.bachelordegreeproject.data.local.repository.SessionRepository
import com.example.bachelordegreeproject.data.remote.mappers.AuthPersonMapper
import com.example.bachelordegreeproject.data.remote.mappers.AuthPlaneMapper
import com.example.bachelordegreeproject.data.remote.request.AuthByLoginRequestModel
import com.example.bachelordegreeproject.data.remote.request.AuthByRfidRequestModel
import com.example.bachelordegreeproject.data.remote.request.StartSessionRequestModel
import com.example.bachelordegreeproject.domain.models.AuthPerson
import com.example.bachelordegreeproject.domain.models.AuthPlane
import com.example.bachelordegreeproject.domain.models.Session
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val rflotHttpService: RflotHttpService,
    private val authPersonMapper: AuthPersonMapper,
    private val authPlaneMapper: AuthPlaneMapper,
    private val sessionRepository: SessionRepository
) : AuthRepository {
    override suspend fun authByLogin(login: String, password: String?): Result<AuthPerson> {
        val params = AuthByLoginRequestModel(login = login, pass = password)
        val result = rflotHttpService.authByLogin(params)
            .map { authPersonMapper.map(it) }
        if (result is Result.Success) sessionRepository.setSession(
            sessionRepository.getSession()?.copy(userId = result.value.userId) ?: Session(
                userId = result.value.userId,
                reportId = null,
                planeId = null
            )
        )
        return result
    }

    override suspend fun authByRfid(rfid: String): Result<AuthPerson> {
        val params = AuthByRfidRequestModel(rfid)
        val result = rflotHttpService.authByRfid(params)
            .map { authPersonMapper.map(it) }
        if (result is Result.Success) sessionRepository.setSession(
            sessionRepository.getSession()?.copy(userId = result.value.userId) ?: Session(
                userId = result.value.userId,
                reportId = null,
                planeId = null
            )
        )
        return result
    }

    override suspend fun authPlane(
        planeId: String,
        typeCheck: String
    ): Result<AuthPlane> {
        val userId = sessionRepository.getSession()?.userId ?: return Result.Fail()
        val params = StartSessionRequestModel(
            idPlane = planeId,
            typeCheck = typeCheck,
            idUser = userId
        )
        val result = rflotHttpService.startSession(params).map { authPlaneMapper.map(it) }
        if (result is Result.Success) sessionRepository.setSession(
            sessionRepository.getSession()?.copy(userId = result.value.reportId) ?: Session(
                reportId = result.value.reportId,
                userId = null,
                planeId = planeId
            )
        )
        return result
    }
}

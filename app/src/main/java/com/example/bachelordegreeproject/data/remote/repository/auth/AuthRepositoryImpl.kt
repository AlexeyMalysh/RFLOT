package com.example.bachelordegreeproject.data.remote.repository.auth

import com.example.bachelordegreeproject.core.network.RflotHttpService
import com.example.bachelordegreeproject.data.local.repository.SessionRepository
import com.example.bachelordegreeproject.data.remote.mappers.AuthPersonMapper
import com.example.bachelordegreeproject.data.remote.mappers.SessionMapper
import com.example.bachelordegreeproject.data.remote.request.AuthRequestModel
import com.example.bachelordegreeproject.data.remote.request.StartSessionRequestModel
import com.example.bachelordegreeproject.domain.models.AuthPerson
import com.example.bachelordegreeproject.domain.models.Session
import com.example.bachelordegreeproject.core.util.constants.Result
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val rflotHttpService: RflotHttpService,
    private val authPersonMapper: AuthPersonMapper,
    private val sessionMapper: SessionMapper,
    private val sessionRepository: SessionRepository
) : AuthRepository {
    override suspend fun authPerson(login: String, password: String?): Result<AuthPerson> {
        val params = AuthRequestModel(login = login, pass = password)
        return rflotHttpService.auth(params)
            .map { authPersonMapper.map(it) }
    }

    override suspend fun authPlane(
        planeId: String,
        typeCheck: String
    ): Result<Session> {
        val params = StartSessionRequestModel(
            idPlane = planeId,
            typeCheck = typeCheck,
            fullNameChecker = "" // TODO delete
        )
        val result = rflotHttpService.startSession(params).map { sessionMapper.map(it) }
        if (result is Result.Success) sessionRepository.setSession(result.value)
        return result
    }
}

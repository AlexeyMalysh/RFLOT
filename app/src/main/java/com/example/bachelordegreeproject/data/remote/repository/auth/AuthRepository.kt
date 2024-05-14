package com.example.bachelordegreeproject.data.remote.repository.auth

import com.example.bachelordegreeproject.core.util.constants.Result
import com.example.bachelordegreeproject.domain.models.AuthPerson
import com.example.bachelordegreeproject.domain.models.AuthPlane

interface AuthRepository {
    suspend fun authByLogin(login: String, password: String?): Result<AuthPerson>
    suspend fun authByRfid(rfid: String): Result<AuthPerson>
    suspend fun authPlane(planeId: String, typeCheck: String): Result<AuthPlane>
}

package com.example.bachelordegreeproject.data.remote.repository.auth

import com.example.bachelordegreeproject.domain.models.AuthPerson
import com.example.bachelordegreeproject.domain.models.Session

interface AuthRepository {
    suspend fun authPerson(login: String, password: String?): Result<AuthPerson>

    suspend fun authPlane(planeId: String, typeCheck: String, reviewerName: String): Result<Session>
}
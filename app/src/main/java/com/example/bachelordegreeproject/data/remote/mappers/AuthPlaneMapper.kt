package com.example.bachelordegreeproject.data.remote.mappers

import com.example.bachelordegreeproject.core.util.Mapper
import com.example.bachelordegreeproject.data.remote.response.AuthPlaneResponseModel
import com.example.bachelordegreeproject.domain.models.AuthPlane
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthPlaneMapper @Inject constructor() : Mapper<AuthPlaneResponseModel, AuthPlane> {
    override fun map(input: AuthPlaneResponseModel): AuthPlane = AuthPlane(
        reportId = input.reportId
    )
}

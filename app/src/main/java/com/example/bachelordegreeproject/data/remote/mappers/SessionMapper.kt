package com.example.bachelordegreeproject.data.remote.mappers

import com.example.bachelordegreeproject.core.util.Mapper
import com.example.bachelordegreeproject.data.remote.response.SessionResponseModel
import com.example.bachelordegreeproject.domain.models.Session
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionMapper @Inject constructor() : Mapper<SessionResponseModel, Session> {
    override fun map(input: SessionResponseModel) = Session(
        reportId = input.reportId
    )
}

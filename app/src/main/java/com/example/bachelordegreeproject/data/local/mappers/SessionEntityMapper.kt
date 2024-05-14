package com.example.bachelordegreeproject.data.local.mappers

import com.example.bachelordegreeproject.core.util.Mapper
import com.example.bachelordegreeproject.database.model.SessionEntity
import com.example.bachelordegreeproject.domain.models.Session
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionEntityMapper @Inject constructor() : Mapper<SessionEntity, Session> {
    override fun map(input: SessionEntity) = Session(
        reportId = input.reportId,
        userId = input.userId,
        planeId = input.planeId
    )

    override fun reverse(input: Session) = SessionEntity(
        reportId = input.reportId,
        userId = input.userId,
        planeId = input.planeId
    )
}

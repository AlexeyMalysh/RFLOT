package com.example.bachelordegreeproject.data.remote.mappers

import com.example.bachelordegreeproject.core.util.Mapper
import com.example.bachelordegreeproject.data.remote.response.AuthResponseModel
import com.example.bachelordegreeproject.domain.models.AuthPerson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthPersonMapper @Inject constructor() : Mapper<AuthResponseModel, AuthPerson> {
    override fun map(input: AuthResponseModel): AuthPerson = AuthPerson(
        userId = input.userId
    )
}

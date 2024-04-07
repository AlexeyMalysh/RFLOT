package com.example.bachelordegreeproject.di

import com.example.bachelordegreeproject.core.network.RflotHttpService
import com.example.bachelordegreeproject.core.network.RflotHttpServiceImpl
import com.example.bachelordegreeproject.data.remote.repository.auth.AuthRepository
import com.example.bachelordegreeproject.data.remote.repository.auth.AuthRepositoryImpl
import com.example.bachelordegreeproject.data.remote.repository.zones.ZonesRepository
import com.example.bachelordegreeproject.data.remote.repository.zones.ZonesRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface AppBindsModule {
    @Binds
    fun bindRflotHttpService(
        impl: RflotHttpServiceImpl
    ): RflotHttpService

    // Repository
    @Binds
    fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    fun bindZonesRepository(
        impl: ZonesRepositoryImpl
    ): ZonesRepository
}

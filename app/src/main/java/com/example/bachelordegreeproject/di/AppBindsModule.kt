package com.example.bachelordegreeproject.di

import com.example.bachelordegreeproject.core.network.ktor.RflotHttpService
import com.example.bachelordegreeproject.core.network.ktor.RflotHttpServiceImpl
import com.example.bachelordegreeproject.core.nfc.NfcReader
import com.example.bachelordegreeproject.core.nfc.NfcReaderImpl
import com.example.bachelordegreeproject.data.remote.repository.auth.AuthRepository
import com.example.bachelordegreeproject.data.remote.repository.auth.AuthRepositoryImpl
import com.example.bachelordegreeproject.data.remote.repository.equip.EquipRepository
import com.example.bachelordegreeproject.data.remote.repository.equip.EquipRepositoryImpl
import com.example.bachelordegreeproject.data.remote.repository.zones.ZonesRepository
import com.example.bachelordegreeproject.data.remote.repository.zones.ZonesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AppBindsModule {
    @Binds
    fun bindRflotHttpService(
        impl: RflotHttpServiceImpl
    ): RflotHttpService

    @Binds
    fun bindNfcReader(impl: NfcReaderImpl): NfcReader

    // Repository
    @Binds
    fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    fun bindZonesRepository(
        impl: ZonesRepositoryImpl
    ): ZonesRepository

    @Binds
    fun bindEquipRepository(
        impl: EquipRepositoryImpl
    ): EquipRepository
}

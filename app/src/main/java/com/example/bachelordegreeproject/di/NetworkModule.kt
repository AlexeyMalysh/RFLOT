package com.example.bachelordegreeproject.di

import androidx.annotation.VisibleForTesting
import com.example.bachelordegreeproject.core.network.RflotHttpService
import com.example.bachelordegreeproject.core.network.RflotHttpServiceImpl
import com.example.bachelordegreeproject.core.util.constants.RflotEndpoint
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.android.Android
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
open class NetworkModule {

    protected open fun internalHttpClientEngine(): HttpClientEngineFactory<*> = Android

    @VisibleForTesting
    internal open val baseUrl = RflotEndpoint.baseUrl

    @Provides
    @ApiUrl
    fun apiUrl(): String = baseUrl

    @Provides
    fun httpClientEngine(): HttpClientEngineFactory<*> = internalHttpClientEngine()
}

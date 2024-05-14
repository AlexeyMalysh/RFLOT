package com.example.bachelordegreeproject.di

import android.content.Context
import android.content.Intent
import com.example.bachelordegreeproject.core.network.socket.SocketClient
import com.example.bachelordegreeproject.core.network.socket.factory.SocketFactory
import com.example.bachelordegreeproject.core.receiver.RingtoneReceiver
import com.example.bachelordegreeproject.data.remote.mappers.EquipStateMapper
import com.example.bachelordegreeproject.data.remote.repository.socket.SocketRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class AppModule {
    @IoDispatcher
    @Singleton
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    @IoDispatcher
    fun providesIoDispatcherSupervisorJobScope(@IoDispatcher coroutineDispatcher: CoroutineDispatcher) =
        CoroutineScope(coroutineDispatcher + SupervisorJob())

    @Provides
    @Named("alarmIntent")
    fun providesRingtoneIntent(@ApplicationContext context: Context): Intent {
        return Intent(context, RingtoneReceiver::class.java).apply {
            action = RingtoneReceiver.INTENT_ACTION
        }
    }

    @Provides
    @Singleton
    fun provideSocketClient(): SocketClient = SocketClient(
        socketFactory = SocketFactory.Client(),
        loggingPrefix = SocketFactory.Client.LOGGING_PREFIX
    )

    @Provides
    @Singleton
    fun providesSocketRepository(
        socketClient: SocketClient,
        equipStateMapper: EquipStateMapper,
        @IoDispatcher coroutineScope: CoroutineScope
    ): SocketRepository = SocketRepository(socketClient, equipStateMapper, coroutineScope)
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApiUrl

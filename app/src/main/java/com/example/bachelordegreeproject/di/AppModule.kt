package com.example.bachelordegreeproject.di

import android.content.Context
import android.content.Intent
import com.example.bachelordegreeproject.core.receiver.RingtoneReceiver
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
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApiUrl

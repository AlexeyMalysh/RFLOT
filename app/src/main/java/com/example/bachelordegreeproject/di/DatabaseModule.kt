package com.example.bachelordegreeproject.di

import android.content.Context
import com.example.bachelordegreeproject.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDb(@ApplicationContext context: Context) = AppDatabase.buildDatabase(context)

    //Dao
    @Provides
    @Singleton
    fun provideSessionDao(appDatabase: AppDatabase) = appDatabase.sessionDao()
}

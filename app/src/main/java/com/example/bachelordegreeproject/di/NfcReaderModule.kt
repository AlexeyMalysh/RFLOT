package com.example.bachelordegreeproject.di

import android.app.Activity
import com.example.bachelordegreeproject.core.nfc.NfcReader
import com.example.bachelordegreeproject.core.nfc.NfcReaderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NfcReaderModule {
    @Provides
    fun provideNfcReader(@ActivityContext activity: Activity): NfcReader {
        return NfcReaderImpl(activity)
    }
}

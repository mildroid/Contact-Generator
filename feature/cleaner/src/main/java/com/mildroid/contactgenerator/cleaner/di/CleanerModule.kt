package com.mildroid.contactgenerator.cleaner.di

import android.content.Context
import androidx.work.WorkManager
import com.mildroid.contactgenerator.cleaner.CleanerImpl
import com.mildroid.contactgenerator.domain.Cleaner
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CleanerModule {

    @Provides
    fun cleanerProvider(workManager: WorkManager): Cleaner {
        return CleanerImpl(workManager)
    }

}
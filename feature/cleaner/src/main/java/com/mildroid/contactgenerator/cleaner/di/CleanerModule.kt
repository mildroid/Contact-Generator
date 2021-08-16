package com.mildroid.contactgenerator.cleaner.di

import androidx.work.WorkManager
import com.mildroid.contactgenerator.cleaner.CleanerImpl
import com.mildroid.contactgenerator.domain.Cleaner
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Dagger Module for [CleanerWorker]
 */
@Module
@InstallIn(SingletonComponent::class)
object CleanerModule {

    @Provides
    fun cleanerProvider(workManager: WorkManager): Cleaner {
        return CleanerImpl(workManager)
    }

}
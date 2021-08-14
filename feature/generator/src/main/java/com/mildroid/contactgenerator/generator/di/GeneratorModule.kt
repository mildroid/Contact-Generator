package com.mildroid.contactgenerator.generator.di

import android.content.Context
import androidx.work.WorkManager
import com.mildroid.contactgenerator.domain.Generator
import com.mildroid.contactgenerator.core.ContactOperations
import com.mildroid.contactgenerator.generator.GeneratorImpl
import com.mildroid.contactgenerator.generator.util.NotificationOperations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeneratorModule {

    @Provides
    @Singleton
    fun generatorProvider(workManager: WorkManager): Generator {
        return GeneratorImpl(workManager)
    }

    @Provides
    @Singleton
    fun provideContactOperations(@ApplicationContext context: Context): ContactOperations {
        return ContactOperations(context)
    }

    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    fun provideNotification(
        @ApplicationContext context: Context,
        workManager: WorkManager
    ): NotificationOperations {

        return NotificationOperations(context, workManager)
    }
}
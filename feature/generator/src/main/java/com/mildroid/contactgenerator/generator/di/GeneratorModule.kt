package com.mildroid.contactgenerator.generator.di

import com.mildroid.contactgenerator.domain.Generator
import com.mildroid.contactgenerator.generator.GeneratorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeneratorModule {

    @Provides
    @Singleton
    fun generatorProvider(): Generator {
        return GeneratorImpl()
    }
}
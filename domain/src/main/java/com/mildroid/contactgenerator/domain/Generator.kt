package com.mildroid.contactgenerator.domain

import com.mildroid.contactgenerator.domain.model.GeneratorParams
import com.mildroid.contactgenerator.domain.model.WorkerInfo
import kotlinx.coroutines.flow.Flow

/**
 * Generator interface for abstraction layer.
 */
interface Generator {

    fun generate(inputData: GeneratorParams)

    fun cancel()

    fun workerDetails(): Flow<WorkerInfo>
}
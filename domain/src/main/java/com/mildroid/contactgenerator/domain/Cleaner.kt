package com.mildroid.contactgenerator.domain

import com.mildroid.contactgenerator.domain.model.WorkerInfo
import kotlinx.coroutines.flow.Flow

/**
 * Cleaner Interface for abstraction layer.
 */
interface Cleaner {

    fun clean()

    fun workerDetails(): Flow<WorkerInfo>
}
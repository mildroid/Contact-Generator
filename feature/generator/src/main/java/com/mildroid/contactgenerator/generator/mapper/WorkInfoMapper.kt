package com.mildroid.contactgenerator.generator.mapper

import androidx.work.WorkInfo
import com.mildroid.contactgenerator.domain.model.WorkerInfo
import com.mildroid.contactgenerator.domain.model.state.WorkerState

/**
 * [WorkInfo] mapper to [WorkerInfo]
 */
fun WorkInfo.toWorkerInfo(): WorkerInfo {
    return WorkerInfo(
        id,
        state.toWorkerState(),
        outputData.toString()
    )
}

/**
 * [WorkInfo] state mapper to [WorkerState]
 */
fun WorkInfo.State.toWorkerState(): WorkerState {
    return when (this) {
        WorkInfo.State.ENQUEUED -> WorkerState.ENQUEUED
        WorkInfo.State.RUNNING -> WorkerState.RUNNING
        WorkInfo.State.SUCCEEDED -> WorkerState.SUCCEEDED
        WorkInfo.State.FAILED -> WorkerState.FAILED
        WorkInfo.State.BLOCKED -> WorkerState.BLOCKED
        WorkInfo.State.CANCELLED -> WorkerState.CANCELED
    }
}


package com.mildroid.contactgenerator.cleaner

import androidx.lifecycle.asFlow
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.mildroid.contactgenerator.core.CLEANER_ONE_TIME_WORK_REQUEST
import com.mildroid.contactgenerator.core.CLEANER_WORKER_NAME
import com.mildroid.contactgenerator.domain.Cleaner
import com.mildroid.contactgenerator.domain.model.WorkerInfo
import com.mildroid.contactgenerator.domain.model.state.WorkerState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * The Implementation for [CleanerWorker].
 */

class CleanerImpl @Inject constructor(
    private val workManager: WorkManager

) : Cleaner {

    private val cleanerRequest =
        OneTimeWorkRequestBuilder<CleanerWorker>()
            .addTag(CLEANER_ONE_TIME_WORK_REQUEST)
            .build()

    override fun clean() {
        workManager
            .beginUniqueWork(
                CLEANER_WORKER_NAME,
                ExistingWorkPolicy.KEEP,
                cleanerRequest
            ).enqueue()
    }

    override fun workerDetails(): Flow<WorkerInfo> {
        return flow {
            emitAll(
                workManager
                    .getWorkInfosByTagLiveData(CLEANER_ONE_TIME_WORK_REQUEST)
                    .asFlow()
                    .map {
                        if (it.size > 0) {
                            it[0].toWorkerInfo()
                        } else {
                            WorkerInfo.idleWorkerInfo()
                        }
                    }
            )
        }
    }

}

// this shouldn't happened two times but...
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

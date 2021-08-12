package com.mildroid.contactgenerator.generator

import androidx.lifecycle.asFlow
import androidx.work.*
import com.mildroid.contactgenerator.core.*
import com.mildroid.contactgenerator.domain.Generator
import com.mildroid.contactgenerator.domain.model.GeneratorParams
import com.mildroid.contactgenerator.domain.model.WorkerInfo
import com.mildroid.contactgenerator.generator.mapper.toWorkerInfo
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GeneratorImpl @Inject constructor(
    private val workManager: WorkManager

) : Generator {

    private lateinit var inputData: GeneratorParams

    private fun workerProvider(): OneTimeWorkRequest {
        val data = with(Data.Builder()) {
            putInt(GENERATOR_WORKER_PARAM_MIN, inputData.min)
            putInt(GENERATOR_WORKER_PARAM_MAX, inputData.max)
            putString(GENERATOR_WORKER_PARAM_TEMPLATE, inputData.template)
        }.build()

        return OneTimeWorkRequestBuilder<GeneratorWorker>()
            .addTag(GENERATOR_ONE_TIME_WORK_REQUEST)
            .setInputData(data)
            .build()
    }

    override fun generate(inputData: GeneratorParams) {
        this.inputData = inputData

        workManager
            .beginUniqueWork(
                GENERATOR_WORKER_NAME,
                ExistingWorkPolicy.REPLACE,
                workerProvider()
            ).enqueue()
    }

    override fun cancel() {
        workManager
            .cancelUniqueWork(GENERATOR_WORKER_NAME)
    }

    override fun workerDetails(): Flow<WorkerInfo> {
        return flow {
            emitAll(
                workManager
                    .getWorkInfosByTagLiveData(GENERATOR_ONE_TIME_WORK_REQUEST)
                    .asFlow()
                    .map {
                        if (it.size > 0)
                            it.first().toWorkerInfo()
                         else
                             WorkerInfo.idleWorkerInfo()
                    }
            )
        }
    }
}
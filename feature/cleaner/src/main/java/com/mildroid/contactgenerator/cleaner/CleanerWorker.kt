package com.mildroid.contactgenerator.cleaner

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mildroid.contactgenerator.core.ContactOperations
import com.mildroid.contactgenerator.core.log
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class CleanerWorker @AssistedInject constructor(
    private val contactOperations: ContactOperations,
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters

) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        contactOperations
            .prepareRemover()

        return Result.success()
    }
}
package com.mildroid.contactgenerator.generator

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.mildroid.contactgenerator.core.GENERATOR_NOTIFICATION_ID
import com.mildroid.contactgenerator.core.GENERATOR_WORKER_PARAM_MAX
import com.mildroid.contactgenerator.core.GENERATOR_WORKER_PARAM_MIN
import com.mildroid.contactgenerator.core.GENERATOR_WORKER_PARAM_TEMPLATE
import com.mildroid.contactgenerator.generator.util.ContactOperations
import com.mildroid.contactgenerator.generator.util.NotificationOperations
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.isActive

@HiltWorker
class GeneratorWorker @AssistedInject constructor(
    private val contactOperations: ContactOperations,
    private val notificationOperations: NotificationOperations,
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters

) : CoroutineWorker(appContext, params) {

    override fun isRunInForeground() = true

    override suspend fun doWork(): Result {
        val min = inputData.getInt(GENERATOR_WORKER_PARAM_MIN, 0)
        val max = inputData.getInt(GENERATOR_WORKER_PARAM_MAX, 0)
        val template = inputData.getString(GENERATOR_WORKER_PARAM_TEMPLATE)

        val notificationManager =
            this.notificationOperations.getNotificationManager()

        val numberList = mutableListOf<String>()
        for (n in min..max) {
            numberList.add("$template $n")
        }

        var n = 0
        numberList.forEach {
            if (currentCoroutineContext().isActive) {
                val updatedNotification = showProgress(numberList.size, n++, it)
                    .build()

                notificationManager
                    ?.notify(GENERATOR_NOTIFICATION_ID, updatedNotification)

                setForeground(ForegroundInfo(GENERATOR_NOTIFICATION_ID, updatedNotification))

                this.contactOperations
                    .prepareContact(it)
            } else {
                notificationManager?.cancel(GENERATOR_NOTIFICATION_ID)
            }
        }

        return Result.success()
    }

    private fun showProgress(max: Int, progress: Int, content: String): NotificationCompat.Builder {
        return this.notificationOperations
            .notification(id)
            .setContentText(content)
            .setProgress(max, progress, false)
    }
}
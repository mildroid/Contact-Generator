package com.mildroid.contactgenerator.generator.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.WorkManager
import com.mildroid.contactgenerator.core.GENERATOR_NOTIFICATION_CHANNEL_ID
import com.mildroid.contactgenerator.core.GENERATOR_NOTIFICATION_CHANNEL_NAME
import com.mildroid.contactgenerator.generator.GeneratorWorker
import com.mildroid.contactgenerator.generator.R
import java.util.*
import javax.inject.Inject

class NotificationOperations @Inject constructor(
    private val context: Context,
    private val workManager: WorkManager
) {

    private val notificationManager =
        ContextCompat.getSystemService(this.context, NotificationManager::class.java)

    private fun channelProvider() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var notificationChannel =
                notificationManager?.getNotificationChannel(GENERATOR_NOTIFICATION_CHANNEL_ID)

            if (notificationChannel == null) {
                notificationChannel = NotificationChannel(
                    GENERATOR_NOTIFICATION_CHANNEL_ID,
                    GENERATOR_NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )

                notificationManager?.createNotificationChannel(notificationChannel)
            }
        }
    }

    fun notification(workerId: UUID): NotificationCompat.Builder {
        channelProvider()

        return NotificationCompat.Builder(this.context, GENERATOR_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(android.R.drawable.sym_contact_card)
                .setAutoCancel(false)
                .setOngoing(false)
                .setOnlyAlertOnce(true)
                .setContentTitle("Generating...")
                .addAction(
                    android.R.drawable.ic_menu_close_clear_cancel,
                    "cancel",
                    this.workManager.createCancelPendingIntent(workerId)
                )
    }

    fun getNotificationManager(): NotificationManager? {
        return this.notificationManager
    }
}
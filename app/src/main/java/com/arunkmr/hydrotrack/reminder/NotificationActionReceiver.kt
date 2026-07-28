package com.arunkmr.hydrotrack.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.arunkmr.hydrotrack.data.local.AppDatabase
import com.arunkmr.hydrotrack.data.local.entity.WaterLogEntity
import com.arunkmr.hydrotrack.widget.HydroWidget
import androidx.glance.appwidget.updateAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return

        when (action) {
            NotificationHelper.ACTION_LOG_REFILL -> {
                NotificationHelper.dismissNotification(context)

                // Log refill asynchronously
                val pendingResult = goAsync()
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val db = AppDatabase.getInstance(context)
                        val settings = db.settingsDao().getSettingsSync()
                        val bottleSize = settings?.bottleSizeMl ?: 850

                        val log = WaterLogEntity(
                            timestampEpochMs = System.currentTimeMillis(),
                            amountMl = bottleSize
                        )
                        db.waterLogDao().insert(log)

                        // Refresh Glance widgets
                        HydroWidget().updateAll(context)
                    } finally {
                        pendingResult.finish()
                    }
                }
            }

            NotificationHelper.ACTION_SNOOZE -> {
                NotificationHelper.dismissNotification(context)
                ReminderScheduler.scheduleSnooze(context)
            }
        }
    }
}

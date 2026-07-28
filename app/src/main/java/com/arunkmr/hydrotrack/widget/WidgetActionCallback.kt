package com.arunkmr.hydrotrack.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.updateAll
import com.arunkmr.hydrotrack.data.local.AppDatabase
import com.arunkmr.hydrotrack.data.local.entity.WaterLogEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WidgetActionCallback : ActionCallback {

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            val settings = db.settingsDao().getSettingsSync()
            val bottleSize = settings?.bottleSizeMl ?: 850

            val log = WaterLogEntity(
                timestampEpochMs = System.currentTimeMillis(),
                amountMl = bottleSize
            )

            db.waterLogDao().insert(log)
        }

        // Immediately refresh Glance widget state
        HydroWidget().updateAll(context)
    }
}

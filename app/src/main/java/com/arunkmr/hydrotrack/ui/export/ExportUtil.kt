package com.arunkmr.hydrotrack.ui.export

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.arunkmr.hydrotrack.data.local.DailyTotal
import com.arunkmr.hydrotrack.data.local.entity.SymptomLogEntity
import com.arunkmr.hydrotrack.data.local.entity.UserSettingsEntity
import java.io.File
import java.time.LocalDate

object ExportUtil {

    /**
     * Generate CSV content for hydration and symptom logs over a date range.
     */
    fun generateCsvContent(
        dailyTotals: List<DailyTotal>,
        symptoms: List<SymptomLogEntity>,
        targetMl: Int
    ): String {
        val sb = java.lang.StringBuilder()
        sb.append("Date,Total Water (ml),Target (ml),Goal Reached,Felt Dizzy,Dry Skin,Notes\n")

        val symptomMap = symptoms.associateBy { it.dateEpochDay }
        val totalsMap = dailyTotals.associateBy { it.dateEpochDay }

        // Last 30 days
        val today = LocalDate.now().toEpochDay()
        for (day in (today - 29)..today) {
            val dateStr = LocalDate.ofEpochDay(day).toString()
            val total = totalsMap[day]?.totalMl ?: 0
            val isGoalMet = total >= targetMl
            val symptom = symptomMap[day]

            val dizzyStr = if (symptom?.feltDizzy == true) "Yes" else "No"
            val drySkinStr = if (symptom?.drySkin == true) "Yes" else "No"
            val notesStr = symptom?.notes?.replace(",", ";")?.replace("\n", " ") ?: ""

            sb.append("$dateStr,$total,$targetMl,${if (isGoalMet) "Yes" else "No"},$dizzyStr,$drySkinStr,\"$notesStr\"\n")
        }

        return sb.toString()
    }

    /**
     * Share CSV file via Android Share Sheet.
     */
    fun shareCsvReport(
        context: Context,
        dailyTotals: List<DailyTotal>,
        symptoms: List<SymptomLogEntity>,
        settings: UserSettingsEntity
    ) {
        val targetMl = if (settings.useManualOverride && settings.manualTargetMl != null) {
            settings.manualTargetMl
        } else {
            (settings.weightKg * 35).toInt()
        }

        val csvText = generateCsvContent(dailyTotals, symptoms, targetMl)
        val file = File(context.cacheDir, "HydroTrack_Doctor_Report.csv")
        file.writeText(csvText)

        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/csv"
            putExtra(Intent.EXTRA_SUBJECT, "HydroTrack Hydration & Symptom Log (Doctor Visit)")
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(intent, "Share Doctor Report"))
    }
}

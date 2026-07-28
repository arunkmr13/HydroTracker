package com.arunkmr.hydrotrack.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arunkmr.hydrotrack.data.repository.SettingsRepository
import com.arunkmr.hydrotrack.data.repository.WaterRepository
import com.arunkmr.hydrotrack.domain.TargetCalculator
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class HistoryViewModel(
    private val waterRepository: WaterRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val startDate = LocalDate.now().minusDays(29)
    private val endDate = LocalDate.now()

    val uiState: StateFlow<HistoryUiState> = combine(
        waterRepository.getLogsInRange(startDate, endDate),
        waterRepository.getSymptomsInRange(startDate, endDate),
        settingsRepository.getSettings()
    ) { logs, symptoms, settings ->
        if (settings == null) {
            HistoryUiState(isLoading = true)
        } else {
            val targetMl = TargetCalculator.calculateTarget(settings)
            val zone = ZoneId.systemDefault()

            // Group logs by Local Date to avoid UTC timezone offsets
            val totalsMap = logs.groupBy {
                Instant.ofEpochMilli(it.timestampEpochMs).atZone(zone).toLocalDate().toEpochDay()
            }.mapValues { (_, dayLogs) ->
                dayLogs.sumOf { it.amountMl }
            }

            val symptomsMap = symptoms.associateBy { it.dateEpochDay }

            val today = LocalDate.now()
            val dayItems = mutableListOf<DayHistoryItem>()

            // Compute last 30 days
            for (i in 29 downTo 0) {
                val date = today.minusDays(i.toLong())
                val epochDay = date.toEpochDay()
                val total = totalsMap[epochDay] ?: 0
                val safeTarget = targetMl.coerceAtLeast(1)
                val fraction = (total.toFloat() / safeTarget).coerceIn(0f, 1f)

                dayItems.add(
                    DayHistoryItem(
                        dateEpochDay = epochDay,
                        dateString = date.format(DateTimeFormatter.ofPattern("MMM d")),
                        totalMl = total,
                        targetMl = targetMl,
                        progressFraction = fraction,
                        isGoalMet = total >= targetMl,
                        symptom = symptomsMap[epochDay]
                    )
                )
            }

            // Calculate rolling 7-day average (last 7 days)
            // Fix: Divide total intake over last 7 days by active days with logged data (or days elapsed)
            val last7Days = dayItems.takeLast(7)
            val activeDaysCount = last7Days.count { it.totalMl > 0 }.coerceAtLeast(1)
            val totalIntakeLast7Days = last7Days.sumOf { it.totalMl }
            val avg7Days = (totalIntakeLast7Days.toDouble() / activeDaysCount).roundToInt()

            HistoryUiState(
                rolling7DayAverageMl = avg7Days,
                targetMl = targetMl,
                last30Days = dayItems,
                isLoading = false
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HistoryUiState(isLoading = true)
    )
}

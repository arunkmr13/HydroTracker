package com.arunkmr.hydrotrack.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.Button
import androidx.glance.ButtonDefaults
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.LinearProgressIndicator
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.arunkmr.hydrotrack.data.local.AppDatabase
import com.arunkmr.hydrotrack.data.local.entity.WaterLogEntity
import com.arunkmr.hydrotrack.domain.TargetCalculator
import com.arunkmr.hydrotrack.domain.model.DailyProgress
import com.arunkmr.hydrotrack.ui.theme.HydroTeal
import kotlinx.coroutines.flow.firstOrNull
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class HydroWidget : GlanceAppWidget() {

    companion object {
        private val SMALL_SQUARE = DpSize(110.dp, 110.dp)
        private val MEDIUM_RECTANGLE = DpSize(250.dp, 110.dp)
        private val LARGE_RECTANGLE = DpSize(250.dp, 200.dp)
    }

    override val sizeMode: SizeMode = SizeMode.Responsive(
        setOf(SMALL_SQUARE, MEDIUM_RECTANGLE, LARGE_RECTANGLE)
    )

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val db = AppDatabase.getInstance(context)

        val zone = ZoneId.systemDefault()
        val today = LocalDate.now()
        val startMs = today.atStartOfDay(zone).toInstant().toEpochMilli()
        val endMs = today.plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli() - 1

        val settings = db.settingsDao().getSettingsSync()
        val todayTotal = db.waterLogDao().getTotalForDay(startMs, endMs).firstOrNull() ?: 0
        val todayLogs = db.waterLogDao().getLogsForDay(startMs, endMs).firstOrNull() ?: emptyList()

        val currentMl = todayTotal
        val targetMl = if (settings != null) TargetCalculator.calculateTarget(settings) else 2450
        val bottleSizeMl = settings?.bottleSizeMl ?: 850

        val progress = DailyProgress.from(
            currentMl = currentMl,
            targetMl = targetMl,
            bottleSizeMl = bottleSizeMl
        )

        provideContent {
            GlanceTheme {
                WidgetContent(
                    progress = progress,
                    bottleSizeMl = bottleSizeMl,
                    todayLogs = todayLogs
                )
            }
        }
    }

    @Composable
    private fun WidgetContent(
        progress: DailyProgress,
        bottleSizeMl: Int,
        todayLogs: List<WaterLogEntity>
    ) {
        val currentSize = androidx.glance.LocalSize.current

        val containerColor = ColorProvider(androidx.compose.ui.graphics.Color(0xFF0F1A24))
        val primaryTeal = ColorProvider(HydroTeal)
        val textOnDark = ColorProvider(androidx.compose.ui.graphics.Color.White)
        val textSubtle = ColorProvider(androidx.compose.ui.graphics.Color(0xFFB0BEC5))

        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(containerColor)
                .padding(12.dp)
                .cornerRadius(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            when {
                currentSize.height >= LARGE_RECTANGLE.height -> {
                    // Large Widget Layout (4x3) with Mini History
                    LargeWidgetLayout(
                        progress = progress,
                        bottleSizeMl = bottleSizeMl,
                        todayLogs = todayLogs,
                        primaryTeal = primaryTeal,
                        textOnDark = textOnDark,
                        textSubtle = textSubtle
                    )
                }
                currentSize.width >= MEDIUM_RECTANGLE.width -> {
                    // Medium Widget Layout (4x2)
                    MediumWidgetLayout(
                        progress = progress,
                        bottleSizeMl = bottleSizeMl,
                        primaryTeal = primaryTeal,
                        textOnDark = textOnDark,
                        textSubtle = textSubtle
                    )
                }
                else -> {
                    // Small Widget Layout (2x2)
                    SmallWidgetLayout(
                        progress = progress,
                        bottleSizeMl = bottleSizeMl,
                        primaryTeal = primaryTeal,
                        textOnDark = textOnDark,
                        textSubtle = textSubtle
                    )
                }
            }
        }
    }

    @Composable
    private fun SmallWidgetLayout(
        progress: DailyProgress,
        bottleSizeMl: Int,
        primaryTeal: ColorProvider,
        textOnDark: ColorProvider,
        textSubtle: ColorProvider
    ) {
        val percent = (progress.progressFraction * 100).toInt()

        Text(
            text = "💧 HydroTrack",
            style = TextStyle(
                color = primaryTeal,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = GlanceModifier.height(4.dp))

        Text(
            text = "$percent%",
            style = TextStyle(
                color = textOnDark,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = "${progress.currentMl}/${progress.targetMl} ml",
            style = TextStyle(
                color = textSubtle,
                fontSize = 10.sp
            )
        )

        Spacer(modifier = GlanceModifier.height(6.dp))

        Button(
            text = "+1 Refill",
            onClick = actionRunCallback<WidgetActionCallback>(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = primaryTeal,
                contentColor = textOnDark
            ),
            modifier = GlanceModifier.fillMaxWidth().height(36.dp)
        )
    }

    @Composable
    private fun MediumWidgetLayout(
        progress: DailyProgress,
        bottleSizeMl: Int,
        primaryTeal: ColorProvider,
        textOnDark: ColorProvider,
        textSubtle: ColorProvider
    ) {
        val percent = (progress.progressFraction * 100).toInt()

        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = GlanceModifier.defaultWeight()) {
                Text(
                    text = "💧 HydroTrack",
                    style = TextStyle(
                        color = primaryTeal,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = GlanceModifier.height(2.dp))

                Text(
                    text = "${progress.currentMl} / ${progress.targetMl} ml ($percent%)",
                    style = TextStyle(
                        color = textOnDark,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                val bottleFormatted = String.format("%.1f", progress.currentMl.toFloat() / bottleSizeMl)
                Text(
                    text = "$bottleFormatted refills logged today",
                    style = TextStyle(
                        color = textSubtle,
                        fontSize = 11.sp
                    )
                )
            }

            Spacer(modifier = GlanceModifier.width(8.dp))

            Button(
                text = "+1 Refill (+${bottleSizeMl}ml)",
                onClick = actionRunCallback<WidgetActionCallback>(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = primaryTeal,
                    contentColor = textOnDark
                ),
                modifier = GlanceModifier.height(40.dp)
            )
        }

        Spacer(modifier = GlanceModifier.height(8.dp))

        LinearProgressIndicator(
            progress = progress.progressFraction,
            color = primaryTeal,
            backgroundColor = ColorProvider(androidx.compose.ui.graphics.Color(0xFF263238)),
            modifier = GlanceModifier.fillMaxWidth().height(6.dp)
        )
    }

    @Composable
    private fun LargeWidgetLayout(
        progress: DailyProgress,
        bottleSizeMl: Int,
        todayLogs: List<WaterLogEntity>,
        primaryTeal: ColorProvider,
        textOnDark: ColorProvider,
        textSubtle: ColorProvider
    ) {
        val percent = (progress.progressFraction * 100).toInt()

        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = GlanceModifier.defaultWeight()) {
                Text(
                    text = "💧 HydroTrack",
                    style = TextStyle(color = primaryTeal, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "${progress.currentMl} / ${progress.targetMl} ml ($percent%)",
                    style = TextStyle(color = textOnDark, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                )
            }

            Button(
                text = "+1 Refill (+${bottleSizeMl}ml)",
                onClick = actionRunCallback<WidgetActionCallback>(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = primaryTeal,
                    contentColor = textOnDark
                ),
                modifier = GlanceModifier.height(36.dp)
            )
        }

        Spacer(modifier = GlanceModifier.height(6.dp))

        LinearProgressIndicator(
            progress = progress.progressFraction,
            color = primaryTeal,
            backgroundColor = ColorProvider(androidx.compose.ui.graphics.Color(0xFF263238)),
            modifier = GlanceModifier.fillMaxWidth().height(6.dp)
        )

        Spacer(modifier = GlanceModifier.height(8.dp))

        Text(
            text = "Today\'s Activity",
            style = TextStyle(color = primaryTeal, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = GlanceModifier.height(4.dp))

        if (todayLogs.isEmpty()) {
            Text(
                text = "No refills logged yet today.",
                style = TextStyle(color = textSubtle, fontSize = 10.sp)
            )
        } else {
            val formatter = DateTimeFormatter.ofPattern("h:mm a")
            todayLogs.take(2).forEach { log ->
                val timeStr = formatter.format(Instant.ofEpochMilli(log.timestampEpochMs).atZone(ZoneId.systemDefault()))
                Text(
                    text = "• +${log.amountMl} ml refill at $timeStr",
                    style = TextStyle(color = textOnDark, fontSize = 11.sp)
                )
            }
        }
    }
}

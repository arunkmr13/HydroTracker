package com.arunkmr.hydrotrack.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arunkmr.hydrotrack.ui.theme.EncouragingGreen
import com.arunkmr.hydrotrack.ui.theme.GentleWarning
import com.arunkmr.hydrotrack.ui.theme.HydroTeal
import com.arunkmr.hydrotrack.ui.theme.ProgressComplete
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedDay by remember { mutableStateOf<DayHistoryItem?>(null) }
    var currentYearMonth by remember { mutableStateOf(YearMonth.now()) }

    com.arunkmr.hydrotrack.ui.theme.GlassBackground(
        modifier = modifier.fillMaxSize()
    ) {
        if (uiState.isLoading) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(color = HydroTeal)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "HYDRATION HISTORY",
                    style = MaterialTheme.typography.labelMedium,
                    color = HydroTeal,
                    letterSpacing = 1.2.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Monthly Calendar",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Rolling 7-Day Average Glass Card
                com.arunkmr.hydrotrack.ui.theme.GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    cornerRadius = 20.dp,
                    contentPadding = 20.dp
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "7-DAY ROLLING AVERAGE",
                                style = MaterialTheme.typography.labelSmall,
                                color = HydroTeal,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${uiState.rolling7DayAverageMl} ml / day",
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "Target: ${uiState.targetMl} ml",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            tint = HydroTeal,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Calendar Glass Card Header with Navigation
                com.arunkmr.hydrotrack.ui.theme.GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    cornerRadius = 24.dp,
                    contentPadding = 16.dp
                ) {
                    Column {
                        // Month Navigation Header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { currentYearMonth = currentYearMonth.minusMonths(1) }) {
                                Icon(Icons.Default.ChevronLeft, contentDescription = "Previous Month", tint = HydroTeal)
                            }

                            Text(
                                text = currentYearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            IconButton(onClick = { currentYearMonth = currentYearMonth.plusMonths(1) }) {
                                Icon(Icons.Default.ChevronRight, contentDescription = "Next Month", tint = HydroTeal)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Day of Week Header Row (Mon, Tue, Wed, Thu, Fri, Sat, Sun)
                        val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            daysOfWeek.forEach { dayName ->
                                Text(
                                    text = dayName,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Calendar Grid Days Calculation
                        val firstOfMonth = currentYearMonth.atDay(1)
                        val daysInMonth = currentYearMonth.lengthOfMonth()
                        val dayOfWeekOffset = firstOfMonth.dayOfWeek.value - 1 // 0 for Mon, 6 for Sun

                        val historyMap = uiState.last30Days.associateBy { it.dateEpochDay }
                        val totalSlots = dayOfWeekOffset + daysInMonth
                        val numRows = (totalSlots + 6) / 7

                        for (row in 0 until numRows) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                for (col in 0 until 7) {
                                    val dayIndex = row * 7 + col - dayOfWeekOffset + 1
                                    if (dayIndex in 1..daysInMonth) {
                                        val date = currentYearMonth.atDay(dayIndex)
                                        val epochDay = date.toEpochDay()
                                        val item = historyMap[epochDay] ?: DayHistoryItem(
                                            dateEpochDay = epochDay,
                                            dateString = date.format(DateTimeFormatter.ofPattern("MMM d")),
                                            totalMl = 0,
                                            targetMl = uiState.targetMl,
                                            progressFraction = 0f,
                                            isGoalMet = false,
                                            symptom = null
                                        )

                                        CalendarDayTile(
                                            dayNumber = dayIndex,
                                            item = item,
                                            isToday = date == LocalDate.now(),
                                            onClick = { selectedDay = item },
                                            modifier = Modifier.weight(1f)
                                        )
                                    } else {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    // Detail Dialog when tapping any calendar day
    selectedDay?.let { item ->
        AlertDialog(
            onDismissRequest = { selectedDay = null },
            title = {
                Text(
                    text = item.dateString,
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            text = {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total Water:")
                        Text(
                            text = "${item.totalMl} / ${item.targetMl} ml",
                            fontWeight = FontWeight.Bold,
                            color = if (item.isGoalMet) ProgressComplete else MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Goal Status:")
                        Text(
                            text = if (item.isGoalMet) "Goal Reached 💧" else if (item.totalMl > 0) "In Progress" else "No Data Logged",
                            color = if (item.isGoalMet) EncouragingGreen else GentleWarning,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Symptom Check-In:",
                        style = MaterialTheme.typography.titleSmall
                    )

                    if (item.symptom != null) {
                        if (item.symptom.feltDizzy) Text("• Felt Dizzy", color = GentleWarning)
                        if (item.symptom.drySkin) Text("• Dry Skin", color = GentleWarning)
                        if (!item.symptom.feltDizzy && !item.symptom.drySkin) Text("• No symptoms reported", color = EncouragingGreen)
                        if (!item.symptom.notes.isNullOrBlank()) Text("Notes: ${item.symptom.notes}")
                    } else {
                        Text("No symptom check-in recorded for this day.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { selectedDay = null }) {
                    Text("Close", color = HydroTeal)
                }
            }
        )
    }
}

@Composable
fun CalendarDayTile(
    dayNumber: Int,
    item: DayHistoryItem,
    isToday: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tileColor = when {
        item.isGoalMet -> ProgressComplete
        item.totalMl > 0 -> HydroTeal.copy(alpha = 0.5f)
        else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    }

    val textColor = when {
        item.isGoalMet -> Color.White
        item.totalMl > 0 -> Color.White
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(tileColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = dayNumber.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = if (isToday) FontWeight.ExtraBold else FontWeight.Normal),
                color = textColor
            )
            if (isToday) {
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )
            }
        }
    }
}

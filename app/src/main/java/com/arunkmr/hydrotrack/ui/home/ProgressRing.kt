package com.arunkmr.hydrotrack.ui.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arunkmr.hydrotrack.domain.model.DailyProgress
import com.arunkmr.hydrotrack.ui.theme.ProgressComplete
import com.arunkmr.hydrotrack.ui.theme.ProgressEmpty
import com.arunkmr.hydrotrack.ui.theme.ProgressEmptyDark
import com.arunkmr.hydrotrack.ui.theme.ProgressFilled

@Composable
fun ProgressRing(
    progress: DailyProgress,
    modifier: Modifier = Modifier,
    size: Dp = 260.dp,
    strokeWidth: Dp = 20.dp
) {
    val isDark = isSystemInDarkTheme()
    val emptyColor = if (isDark) ProgressEmptyDark else ProgressEmpty
    val filledColor = if (progress.isGoalMet) ProgressComplete else ProgressFilled

    // Animate progress arc sweep
    val animatedFraction by animateFloatAsState(
        targetValue = progress.progressFraction,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "ProgressRingSweep"
    )

    val percent = (progress.progressFraction * 100).toInt()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size)
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val strokePx = strokeWidth.toPx()
            val arcSize = size.toPx() - strokePx

            // Background Track Arc
            drawArc(
                color = emptyColor,
                startAngle = 135f,
                sweepAngle = 270f,
                useCenter = false,
                style = Stroke(width = strokePx, cap = StrokeCap.Round),
                topLeft = androidx.compose.ui.geometry.Offset(strokePx / 2, strokePx / 2),
                size = androidx.compose.ui.geometry.Size(arcSize, arcSize)
            )

            // Foreground Filled Arc
            drawArc(
                color = filledColor,
                startAngle = 135f,
                sweepAngle = 270f * animatedFraction,
                useCenter = false,
                style = Stroke(width = strokePx, cap = StrokeCap.Round),
                topLeft = androidx.compose.ui.geometry.Offset(strokePx / 2, strokePx / 2),
                size = androidx.compose.ui.geometry.Size(arcSize, arcSize)
            )
        }

        // Center Content Text
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "$percent%",
                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${progress.currentMl} / ${progress.targetMl} ml",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(6.dp))

            val bottleFormatted = String.format("%.1f", progress.currentMl.toFloat() / progress.bottleSizeMl)
            Text(
                text = "$bottleFormatted bottles",
                style = MaterialTheme.typography.labelMedium,
                color = filledColor,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

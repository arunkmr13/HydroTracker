package com.arunkmr.hydrotrack.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Premium Crisp Liquid Glass Container for HydroTrack.
 * Translucent frosted glass surface with glowing light refraction borders and 3D depth,
 * without blurring internal text and icon layers.
 */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    elevation: Dp = 8.dp,
    contentPadding: Dp = 20.dp,
    content: @Composable BoxScope.() -> Unit
) {
    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f

    val surfaceBrush = if (isDark) {
        Brush.linearGradient(
            colors = listOf(
                Color(0xFF1B2A3A).copy(alpha = 0.88f),
                Color(0xFF0F1A26).copy(alpha = 0.75f)
            )
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.92f),
                Color.White.copy(alpha = 0.70f)
            )
        )
    }

    val borderBrush = if (isDark) {
        Brush.linearGradient(
            colors = listOf(
                HydroTealLight.copy(alpha = 0.65f),
                Color.White.copy(alpha = 0.30f),
                HydroTeal.copy(alpha = 0.45f)
            )
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.98f),
                Color(0xFFB2EBF2).copy(alpha = 0.70f),
                Color.White.copy(alpha = 0.50f)
            )
        )
    }

    val shape = RoundedCornerShape(cornerRadius)

    Box(
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = shape,
                clip = false,
                ambientColor = if (isDark) Color.Black else HydroTeal.copy(alpha = 0.20f),
                spotColor = if (isDark) HydroTeal.copy(alpha = 0.35f) else HydroTeal.copy(alpha = 0.30f)
            )
            .clip(shape)
            .background(brush = surfaceBrush, shape = shape)
            .border(width = 1.5.dp, brush = borderBrush, shape = shape)
            .padding(contentPadding),
        content = content
    )
}

/**
 * Ambient background wrapper providing rich liquid gradient backdrop for screens.
 */
@Composable
fun GlassBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f

    val bgBrush = if (isDark) {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF09121B),
                Color(0xFF101D2A),
                Color(0xFF070D14)
            )
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFFEBF7F9),
                Color(0xFFD6F0F5),
                Color(0xFFE4F4F7)
            )
        )
    }

    Box(
        modifier = modifier.background(brush = bgBrush),
        content = content
    )
}

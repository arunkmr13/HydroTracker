package com.arunkmr.hydrotrack.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// --- Custom HydroTrack Colors ---
// Calm, non-punitive palette: blues/teals, no red "failure" states

// Primary teal palette
val HydroTeal = Color(0xFF00ACC1)
val HydroTealLight = Color(0xFF4DD0E1)
val HydroTealDark = Color(0xFF00838F)

// Secondary blue palette
val HydroBlue = Color(0xFF5C6BC0)
val HydroBluLight = Color(0xFF8E99F3)
val HydroBlueDark = Color(0xFF3949AB)

// Tertiary calming lavender
val HydroLavender = Color(0xFFB39DDB)
val HydroLavenderLight = Color(0xFFE6CEFF)
val HydroLavenderDark = Color(0xFF836FA9)

// Progress ring colors — never red, always encouraging
val ProgressEmpty = Color(0xFFE0E0E0)        // Light grey for empty ring
val ProgressEmptyDark = Color(0xFF424242)     // Dark grey for empty ring in dark mode
val ProgressFilled = Color(0xFF00BCD4)        // Soft cyan when filling
val ProgressComplete = Color(0xFF26A69A)      // Gentle teal-green when goal met

// Encouraging feedback (not green-as-success, just calm positive)
val EncouragingGreen = Color(0xFF66BB6A)
val GentleWarning = Color(0xFFFFB74D)          // Soft amber, not red

// Backgrounds
val LightBackground = Color(0xFFFAFDFE)
val LightSurface = Color(0xFFFFFFFF)
val DarkBackground = Color(0xFF0F1A24)
val DarkSurface = Color(0xFF1A2733)

// --- Material 3 Color Schemes ---

val LightColorScheme = lightColorScheme(
    primary = HydroTeal,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB2EBF2),
    onPrimaryContainer = Color(0xFF004D56),
    secondary = HydroBlue,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFDBDEFF),
    onSecondaryContainer = Color(0xFF151B5E),
    tertiary = HydroLavender,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFF3E5F5),
    onTertiaryContainer = Color(0xFF4A148C),
    background = LightBackground,
    onBackground = Color(0xFF1A1C1E),
    surface = LightSurface,
    onSurface = Color(0xFF1A1C1E),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFCAC4D0)
)

val DarkColorScheme = darkColorScheme(
    primary = HydroTealLight,
    onPrimary = Color(0xFF003640),
    primaryContainer = HydroTealDark,
    onPrimaryContainer = Color(0xFFB2EBF2),
    secondary = HydroBluLight,
    onSecondary = Color(0xFF1A237E),
    secondaryContainer = HydroBlueDark,
    onSecondaryContainer = Color(0xFFDBDEFF),
    tertiary = HydroLavenderLight,
    onTertiary = Color(0xFF4A148C),
    tertiaryContainer = HydroLavenderDark,
    onTertiaryContainer = Color(0xFFF3E5F5),
    background = DarkBackground,
    onBackground = Color(0xFFE2E2E6),
    surface = DarkSurface,
    onSurface = Color(0xFFE2E2E6),
    surfaceVariant = Color(0xFF2C3440),
    onSurfaceVariant = Color(0xFFC4C6CF),
    outline = Color(0xFF8E9099),
    outlineVariant = Color(0xFF44474F)
)

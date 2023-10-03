package com.cradlesoft.medreminder.android.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MedTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorScheme(
            primary = HealthBlue80,
            onPrimary = HealthBlue20,
            primaryContainer = HealthBlue30,
            onPrimaryContainer = HealthBlue90,
            secondary = CalmTeal80,
            onSecondary = CalmTeal20,
            secondaryContainer = CalmTeal30,
            onSecondaryContainer = CalmTeal90,
            tertiary = ModernGreen80,
            onTertiary = ModernGreen20,
            tertiaryContainer = ModernGreen30,
            onTertiaryContainer = ModernGreen90,
            background = MedicalGreyDark,
            surfaceVariant = MedicalGreyDark
        )
    } else {
        lightColorScheme(
            primary = HealthBlue40,
            onPrimary = CrispWhite,
            primaryContainer = HealthBlue90,
            onPrimaryContainer = HealthBlue10,
            secondary = CalmTeal40,
            onSecondary = CrispWhite,
            secondaryContainer = CalmTeal90,
            onSecondaryContainer = CalmTeal10,
            tertiary = ModernGreen40,
            onTertiary = CrispWhite,
            tertiaryContainer = ModernGreen90,
            onTertiaryContainer = ModernGreen10,
            background = MedicalGrey,
            surfaceVariant = MedicalGrey
        )
    }
    val typography = Typography(
        bodyMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

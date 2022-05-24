package com.althurdinok.fromandwith.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFB3C5FF),
    secondary = Color(0xFFC1C5DC),
    tertiary = Color(0xFFE2BADB),
    error = Color(0xFFFFB4A9),
    background = Color(0xFF1B1B1F),
    outline = Color(0xFF8F909A),
    onPrimary = Color(0xFF00287C),
    onSecondary = Color(0xFF2B3042),
    onTertiary = Color(0xFF422740),
    onError = Color(0xFF680003),
    onBackground = Color(0xFFE3E1E6),
    primaryContainer = Color(0xFF214193),
    secondaryContainer = Color(0xFF414659),
    tertiaryContainer = Color(0xFF5A3D57),
    errorContainer = Color(0xFF930006),
    surface = Color(0xFF1B1B1F),
    surfaceVariant = Color(0xFF44464E),
    onPrimaryContainer = Color(0xFFDAE1FF),
    onSecondaryContainer = Color(0xFFDDE1F9),
    onTertiaryContainer = Color(0xFFFFD7F8),
    onErrorContainer = Color(0xFFFFDAD4),
    onSurface = Color(0xFFE3E1E6),
    onSurfaceVariant = Color(0xFFC6C6D0)
)

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF3D5AAD),
    secondary = Color(0xFF595E71),
    tertiary = Color(0xFF745470),
    error = Color(0xFFBA1B1B),
    background = Color(0xFFFEFBFF),
    outline = Color(0xFF75767F),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onError = Color.White,
    onBackground = Color(0xFF1B1B1F),
    primaryContainer = Color(0xFFDAE1FF),
    secondaryContainer = Color(0xFFDDE1F9),
    tertiaryContainer = Color(0xFFFFD7F8),
    errorContainer = Color(0xFFFFDAD4),
    surface = Color(0xFFFEFBFF),
    surfaceVariant = Color(0xFFE1E1EC),
    onPrimaryContainer = Color(0xFF00164F),
    onSecondaryContainer = Color(0xFF161B2C),
    onTertiaryContainer = Color(0xFF2B122A),
    onErrorContainer = Color(0xFF410001),
    onSurface = Color(0xFF1B1B1F),
    onSurfaceVariant = Color(0xFF44464E)

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun LearnFromAndWithTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }



    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
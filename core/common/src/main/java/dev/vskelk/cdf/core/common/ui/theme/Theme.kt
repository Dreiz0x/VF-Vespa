package dev.vskelk.cdf.core.common.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = VespaColors.Primary,
    onPrimary = VespaColors.OnPrimary,
    primaryContainer = VespaColors.PrimaryContainer,
    background = VespaColors.Background,
    surface = VespaColors.Surface,
    surfaceVariant = VespaColors.SurfaceVariant,
    onPrimaryContainer = VespaColors.TextPrimary,
    onBackground = VespaColors.TextPrimary,
    onSurface = VespaColors.TextPrimary,
    onSurfaceVariant = VespaColors.TextSecondary,
    outline = VespaColors.BorderMedium,
    outlineVariant = VespaColors.BorderSubtle,
    error = VespaColors.Error,
    onError = VespaColors.OnPrimary,
)

@Composable
fun VespaTheme(
    content: @Composable () -> Unit,
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = VespaColors.Background.toArgb()
            window.navigationBarColor = VespaColors.Background.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = false
                isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = VespaTypography.Typography,
        shapes = VespaShapes.Shapes,
        content = content,
    )
}

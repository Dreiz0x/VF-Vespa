package dev.vskelk.cdf.core.common.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

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
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = VespaTypography.Typography,
        shapes = VespaShapes.Shapes,
        content = content,
    )
}

package dev.vskelk.cdf.core.common.ui.theme

import androidx.compose.ui.graphics.Color

object VespaColors {
    // Primary colors
    val Primary = Color(0xFFDEDEDE)
    val OnPrimary = Color(0xFF070707)
    val PrimaryContainer = Color(0xFF2A2A2A)

    // Background and surface
    val Background = Color(0xFF070707)
    val Surface = Color(0xFF0F0F0F)
    val SurfaceContainer = Color(0xFF141414)
    val SurfaceVariant = Color(0xFF1A1A1A)

    // Text colors
    val TextPrimary = Color(0xFFDEDEDE)
    val TextSecondary = Color(0x99FFFFFF)
    val TextMuted = Color(0x1AFFFFFF)

    // Border colors
    val BorderSubtle = Color(0x1AFFFFFF)
    val BorderMedium = Color(0x33FFFFFF)

    // Status colors
    val Online = Color(0xFF4ADE80)
    val Offline = Color(0xFFA0A0A0)
    val AccentRed = Color(0xFFFF6B6B)
    val AccentGreen = Color(0xFF4ADE80)
    val AccentOrange = Color(0xFFFBBF24)
    val AccentBlue = Color(0xFF60A5FA)

    // Alias for Error
    val Error = AccentRed
}

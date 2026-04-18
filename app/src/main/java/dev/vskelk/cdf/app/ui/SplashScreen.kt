package dev.vskelk.cdf.app.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.vskelk.cdf.app.R
import kotlinx.coroutines.delay

private val Background = Color(0xFF070707)
private val TextPrimary = Color(0xFFDEDEDE)
private val TextSecondary = Color(0x55FFFFFF)
private val TextMuted = Color(0x1AFFFFFF)
private val BorderSubtle = Color(0x1AFFFFFF)

private data class SplashAnimationState(
    val logoAlpha: Float,
    val logoScale: Float,
    val titleAlpha: Float,
    val welcomeAlpha: Float,
    val buttonAlpha: Float,
)

@Suppress("FunctionNaming")
@Composable
fun VespaSplashScreen(onStart: () -> Unit) {
    var logoVisible by remember { mutableStateOf(false) }
    var titleVisible by remember { mutableStateOf(false) }
    var welcomeVisible by remember { mutableStateOf(false) }
    var buttonVisible by remember { mutableStateOf(false) }

    val anim = SplashAnimationState(
        logoAlpha = animateFloat(logoVisible, 900),
        logoScale = animateFloat(logoVisible, 900, from = 0.85f),
        titleAlpha = animateFloat(titleVisible, 700),
        welcomeAlpha = animateFloat(welcomeVisible, 600),
        buttonAlpha = animateFloat(buttonVisible, 500),
    )

    LaunchedEffect(Unit) {
        delay(300); logoVisible = true
        delay(800); titleVisible = true
        delay(600); welcomeVisible = true
        delay(600); buttonVisible = true
    }

    SplashContent(anim = anim, onStart = onStart)
}

@Composable
private fun animateFloat(
    visible: Boolean,
    duration: Int,
    from: Float = 0f,
): Float {
    val target = if (visible) 1f else from
    return animateFloatAsState(
        targetValue = target,
        animationSpec = tween(duration, easing = FastOutSlowInEasing),
        label = "anim",
    ).value
}

@Suppress("FunctionNaming")
@Composable
private fun SplashContent(anim: SplashAnimationState, onStart: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().background(Background),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 32.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_vespa_logo),
                contentDescription = "Vespa",
                modifier = Modifier
                    .size(180.dp)
                    .scale(anim.logoScale)
                    .alpha(anim.logoAlpha),
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "VESPA",
                color = TextPrimary,
                fontSize = 72.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 12.sp,
                modifier = Modifier.alpha(anim.titleAlpha),
            )
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .size(width = 64.dp, height = 1.dp)
                    .background(TextPrimary.copy(alpha = 0.2f * anim.titleAlpha)),
            )
            Text(
                text = "SPEN · PREPARACIÓN INTEGRAL · v2.0",
                color = TextMuted,
                fontSize = 9.sp,
                letterSpacing = 2.sp,
                modifier = Modifier.alpha(anim.titleAlpha).padding(bottom = 40.dp),
            )
            Text(
                text = "BIENVENIDO",
                color = TextSecondary,
                fontSize = 10.sp,
                letterSpacing = 4.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.alpha(anim.welcomeAlpha),
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Reydesel",
                color = TextPrimary.copy(alpha = 0.85f),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.sp,
                modifier = Modifier.alpha(anim.welcomeAlpha),
            )
            Spacer(modifier = Modifier.height(40.dp))
            SplashButton(alpha = anim.buttonAlpha, onStart = onStart)
        }
        Text(
            text = "INE · DISTRITO 06  ·  CHIHUAHUA  ·  SPE 2026",
            color = TextMuted,
            fontSize = 8.sp,
            letterSpacing = 2.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 28.dp)
                .alpha(anim.buttonAlpha),
        )
    }
}

@Suppress("FunctionNaming")
@Composable
private fun SplashButton(alpha: Float, onStart: () -> Unit) {
    val colors = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = TextPrimary.copy(alpha = 0.7f),
    )
    Button(
        onClick = onStart,
        colors = colors,
        border = BorderStroke(1.dp, BorderSubtle.copy(alpha = 0.4f)),
        modifier = Modifier.alpha(alpha),
    ) {
        Text(
            text = "COMENZAR",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 4.sp,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
        )
    }
}

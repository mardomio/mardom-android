package io.mardom.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import io.mardom.BuildConfig
import io.mardom.ui.RtlLayout

private val darkColorScheme = darkColorScheme(
    primary = AppColors.Dark.primary,
    onPrimary = AppColors.Dark.onPrimary,
    primaryContainer = AppColors.Dark.primaryContainer,
    onPrimaryContainer = AppColors.Dark.onPrimaryContainer,
    secondary = AppColors.Dark.secondary,
    onSecondary = AppColors.Dark.onSecondary,
    secondaryContainer = AppColors.Dark.secondaryContainer,
    onSecondaryContainer = AppColors.Dark.onSecondaryContainer,
    tertiary = AppColors.Dark.tertiary,
    onTertiary = AppColors.Dark.onTertiary,
    tertiaryContainer = AppColors.Dark.tertiaryContainer,
    onTertiaryContainer = AppColors.Dark.onTertiaryContainer,
    error = AppColors.Dark.error,
    errorContainer = AppColors.Dark.errorContainer,
    onError = AppColors.Dark.onError,
    onErrorContainer = AppColors.Dark.onErrorContainer,
    background = AppColors.Dark.background,
    onBackground = AppColors.Dark.onBackground,
    surface = AppColors.Dark.surface,
    onSurface = AppColors.Dark.onSurface,
    surfaceVariant = AppColors.Dark.surfaceVariant,
    onSurfaceVariant = AppColors.Dark.onSurfaceVariant,
    outline = AppColors.Dark.outline,
    inverseOnSurface = AppColors.Dark.inverseOnSurface,
    inverseSurface = AppColors.Dark.inverseSurface,
    inversePrimary = AppColors.Dark.inversePrimary,
    surfaceTint = AppColors.Dark.surfaceTint,
    outlineVariant = AppColors.Dark.outlineVariant,
    scrim = AppColors.Dark.scrim,
)

private val lightColorScheme = lightColorScheme(
    primary = AppColors.Light.primary,
    onPrimary = AppColors.Light.onPrimary,
    primaryContainer = AppColors.Light.primaryContainer,
    onPrimaryContainer = AppColors.Light.onPrimaryContainer,
    secondary = AppColors.Light.secondary,
    onSecondary = AppColors.Light.onSecondary,
    secondaryContainer = AppColors.Light.secondaryContainer,
    onSecondaryContainer = AppColors.Light.onSecondaryContainer,
    tertiary = AppColors.Light.tertiary,
    onTertiary = AppColors.Light.onTertiary,
    tertiaryContainer = AppColors.Light.tertiaryContainer,
    onTertiaryContainer = AppColors.Light.onTertiaryContainer,
    error = AppColors.Light.error,
    errorContainer = AppColors.Light.errorContainer,
    onError = AppColors.Light.onError,
    onErrorContainer = AppColors.Light.onErrorContainer,
    background = AppColors.Light.background,
    onBackground = AppColors.Light.onBackground,
    surface = AppColors.Light.surface,
    onSurface = AppColors.Light.onSurface,
    surfaceVariant = AppColors.Light.surfaceVariant,
    onSurfaceVariant = AppColors.Light.onSurfaceVariant,
    outline = AppColors.Light.outline,
    inverseOnSurface = AppColors.Light.inverseOnSurface,
    inverseSurface = AppColors.Light.inverseSurface,
    inversePrimary = AppColors.Light.inversePrimary,
    surfaceTint = AppColors.Light.surfaceTint,
    outlineVariant = AppColors.Light.outlineVariant,
    scrim = AppColors.Light.scrim,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme() || BuildConfig.DEBUG,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) darkColorScheme else lightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.tertiaryContainer.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    RtlLayout {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
        ) {
            ProvideTextStyle(value = MaterialTheme.typography.bodyMedium) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    content = content,
                )
            }
        }
    }
}
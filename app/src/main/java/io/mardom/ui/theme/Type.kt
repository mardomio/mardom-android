package io.mardom.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import io.mardom.R

val YekanBakh = FontFamily(
    Font(R.font.yekanbakh_regular, weight = FontWeight.Normal),
    Font(R.font.yekanbakh_medium, weight = FontWeight.Medium),
)

private val defaultTypography = Typography()
private const val LINE_HEIGHT_SCALE = 1.075
val AppTypography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(
        fontFamily = YekanBakh,
        lineHeight = defaultTypography.displayLarge.lineHeight * LINE_HEIGHT_SCALE
    ),
    displayMedium = defaultTypography.displayMedium.copy(
        fontFamily = YekanBakh,
        lineHeight = defaultTypography.displayMedium.lineHeight * LINE_HEIGHT_SCALE
    ),
    displaySmall = defaultTypography.displaySmall.copy(
        fontFamily = YekanBakh,
        lineHeight = defaultTypography.displaySmall.lineHeight * LINE_HEIGHT_SCALE
    ),
    headlineLarge = defaultTypography.headlineLarge.copy(
        fontFamily = YekanBakh,
        lineHeight = defaultTypography.headlineLarge.lineHeight * LINE_HEIGHT_SCALE
    ),
    headlineMedium = defaultTypography.headlineMedium.copy(
        fontFamily = YekanBakh,
        lineHeight = defaultTypography.headlineMedium.lineHeight * LINE_HEIGHT_SCALE
    ),
    headlineSmall = defaultTypography.headlineSmall.copy(
        fontFamily = YekanBakh,
        lineHeight = defaultTypography.headlineSmall.lineHeight * LINE_HEIGHT_SCALE
    ),
    titleLarge = defaultTypography.titleLarge.copy(
        fontFamily = YekanBakh,
        lineHeight = defaultTypography.titleLarge.lineHeight * LINE_HEIGHT_SCALE
    ),
    titleMedium = defaultTypography.titleMedium.copy(
        fontFamily = YekanBakh,
        lineHeight = defaultTypography.titleMedium.lineHeight * LINE_HEIGHT_SCALE
    ),
    titleSmall = defaultTypography.titleSmall.copy(
        fontFamily = YekanBakh,
        lineHeight = defaultTypography.titleSmall.lineHeight * LINE_HEIGHT_SCALE
    ),
    bodyLarge = defaultTypography.bodyLarge.copy(
        fontFamily = YekanBakh,
        lineHeight = defaultTypography.bodyLarge.lineHeight * LINE_HEIGHT_SCALE
    ),
    bodyMedium = defaultTypography.bodyMedium.copy(
        fontFamily = YekanBakh,
        lineHeight = defaultTypography.bodyMedium.lineHeight * LINE_HEIGHT_SCALE
    ),
    bodySmall = defaultTypography.bodySmall.copy(
        fontFamily = YekanBakh,
        lineHeight = defaultTypography.bodySmall.lineHeight * LINE_HEIGHT_SCALE
    ),
    labelLarge = defaultTypography.labelLarge.copy(
        fontFamily = YekanBakh,
        lineHeight = defaultTypography.labelLarge.lineHeight * LINE_HEIGHT_SCALE
    ),
    labelMedium = defaultTypography.labelMedium.copy(
        fontFamily = YekanBakh,
        lineHeight = defaultTypography.labelMedium.lineHeight * LINE_HEIGHT_SCALE
    ),
    labelSmall = defaultTypography.labelSmall.copy(
        fontFamily = YekanBakh,
        lineHeight = defaultTypography.labelSmall.lineHeight * LINE_HEIGHT_SCALE
    ),
)
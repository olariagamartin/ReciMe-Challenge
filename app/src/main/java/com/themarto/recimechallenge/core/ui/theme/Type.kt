package com.themarto.recimechallenge.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.themarto.recimechallenge.R

private val Newsreader = FontFamily(
    Font(R.font.newsreader_medium, FontWeight.Medium),
    Font(R.font.newsreader_semibold, FontWeight.SemiBold),
    Font(R.font.newsreader_bold, FontWeight.Bold),
)

private val PlusJakartaSans = FontFamily(
    Font(R.font.plus_jakarta_sans_regular, FontWeight.Normal),
    Font(R.font.plus_jakarta_sans_medium, FontWeight.Medium),
    Font(R.font.plus_jakarta_sans_semibold, FontWeight.SemiBold),
    Font(R.font.plus_jakarta_sans_bold, FontWeight.Bold),
)

private fun editorial(
    size: Int,
    weight: FontWeight,
    lineHeight: Int,
    spacing: TextUnit = 0.em,
) = TextStyle(
    fontFamily = Newsreader,
    fontWeight = weight,
    fontSize = size.sp,
    lineHeight = lineHeight.sp,
    letterSpacing = spacing,
)

private fun ui(
    size: Int,
    weight: FontWeight,
    lineHeight: Int,
    spacing: TextUnit = 0.em,
) = TextStyle(
    fontFamily = PlusJakartaSans,
    fontWeight = weight,
    fontSize = size.sp,
    lineHeight = lineHeight.sp,
    letterSpacing = spacing,
)

val Typography = Typography(
    displayLarge = editorial(44, FontWeight.SemiBold, 52, (-0.02).em),
    displayMedium = editorial(36, FontWeight.SemiBold, 44, (-0.015).em),
    displaySmall = editorial(32, FontWeight.SemiBold, 40),
    headlineLarge = editorial(26, FontWeight.SemiBold, 34),
    headlineMedium = editorial(24, FontWeight.Medium, 32),
    headlineSmall = editorial(20, FontWeight.Medium, 28),
    titleLarge = ui(20, FontWeight.Bold, 28),
    titleMedium = ui(16, FontWeight.SemiBold, 24, 0.01.em),
    titleSmall = ui(14, FontWeight.SemiBold, 20, 0.01.em),
    bodyLarge = ui(16, FontWeight.Normal, 26),
    bodyMedium = ui(14, FontWeight.Normal, 22),
    bodySmall = ui(12, FontWeight.Normal, 18),
    labelLarge = ui(14, FontWeight.SemiBold, 20, 0.02.em),
    labelMedium = ui(12, FontWeight.SemiBold, 16, 0.03.em),
    labelSmall = ui(11, FontWeight.Bold, 14, 0.04.em),
)

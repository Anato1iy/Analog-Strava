package com.example.auth.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnboardingScreen(
    @StringRes val textRes_1: Int,
    @StringRes val textRes_2: Int,
    @DrawableRes val imageRes: Int,
    @DrawableRes val imageResDark: Int
)
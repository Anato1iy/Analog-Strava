package com.example.auth.adapter

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.auth.R
import com.example.auth.data.OnboardingScreen
import com.example.auth.ui.OnboardingFragment
import com.example.utils.ActBar

class OnboardingAdapter(
    fm: Fragment
) : FragmentStateAdapter(fm) {

    private val screens: List<OnboardingScreen> = listOf(
        OnboardingScreen(
            textRes_1 = R.string.onboarding_1_welcome,
            textRes_2 = R.string.onboarding_1_here,
            imageRes = R.drawable.image_onboarding_1,
            imageResDark = R.drawable.image_onboarding_1_night
        ),
        OnboardingScreen(
            textRes_1 = R.string.onboarding_2_friends,
            textRes_2 = R.string.onboarding_2_fill,
            imageRes = R.drawable.image_onboarding_2,
            imageResDark = R.drawable.image_onboarding_2_night
        ),
        OnboardingScreen(
            textRes_1 = R.string.onboarding_3_activities,
            textRes_2 = R.string.onboarding_3_convenient,
            imageRes = R.drawable.image_onboarding_3,
            imageResDark = R.drawable.image_onboarding_3_night
        )
    )

    override fun getItemCount(): Int {
        return screens.size
    }

    override fun createFragment(position: Int): Fragment {
        val screen = screens[position]
        return OnboardingFragment.newInstance(
            textRes_1 = screen.textRes_1,
            textRes_2 = screen.textRes_2,
            imageRes = screen.imageRes,
            imageResDark = screen.imageResDark,
            position = position
        )
    }
}
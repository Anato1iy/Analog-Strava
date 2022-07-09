package com.example.auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.auth.R
import com.example.auth.data.OnboardingSkip
import com.example.auth.databinding.FragmentAuthBinding
import com.example.auth.databinding.FragmentOnboardingBinding
import com.example.auth.databinding.FragmentOndboardingMainBinding
import com.example.utils.ActBar
import com.example.utils.DarkTheme
import com.example.utils.ViewBindingFragment
import com.example.utils.withArguments

class OnboardingFragment : ViewBindingFragment<FragmentOnboardingBinding>(
    FragmentOnboardingBinding::inflate
) {

    private val skip: OnboardingSkip?
        get() = parentFragment
            .let { it as? OnboardingSkip }
    private val isDarkTheme: DarkTheme?
        get() = activity?.let { it as? DarkTheme }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val image = view.findViewById<ImageView>(R.id.imageViewOnboarding)
        if (isDarkTheme?.isDarkTheme() == true) {
            image.setImageResource(requireArguments().getInt(KEY_IMAGE_DARK))
        } else {
            image.setImageResource(requireArguments().getInt(KEY_IMAGE))
        }

        val text1 = view.findViewById<TextView>(R.id.textViewOnboarding_1)
        text1.setText(requireArguments().getInt(KEY_TEXT_1))
        val text2 = view.findViewById<TextView>(R.id.textViewOnboarding_2)
        text2.setText(requireArguments().getInt(KEY_TEXT_2))
        val position = requireArguments().getInt(KEY_POSITION)
        binding.textViewSkip.setOnClickListener {
            skip?.onboardingSkip(position + 1)
        }
    }

    companion object {
        private const val KEY_TEXT_1 = "text_1"
        private const val KEY_TEXT_2 = "text_2"
        private const val KEY_IMAGE = "image"
        private const val KEY_IMAGE_DARK = "image_dark"
        private const val KEY_POSITION = "position"
        fun newInstance(
            @StringRes textRes_1: Int,
            @StringRes textRes_2: Int,
            @DrawableRes imageRes: Int,
            @DrawableRes imageResDark: Int,
            position: Int
        ): OnboardingFragment {
            return OnboardingFragment().withArguments {
                putInt(KEY_TEXT_1, textRes_1)
                putInt(KEY_TEXT_2, textRes_2)
                putInt(KEY_IMAGE, imageRes)
                putInt(KEY_IMAGE_DARK, imageResDark)
                putInt(KEY_POSITION, position)
            }
        }
    }
}
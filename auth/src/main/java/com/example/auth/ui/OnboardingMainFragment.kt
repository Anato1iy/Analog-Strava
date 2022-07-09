package com.example.auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.auth.R
import com.example.auth.adapter.OnboardingAdapter
import com.example.auth.data.OnboardingSkip
import com.example.auth.databinding.FragmentAuthBinding
import com.example.auth.databinding.FragmentAuthBinding.inflate
import com.example.auth.databinding.FragmentOndboardingMainBinding
import com.example.utils.ActBar
import com.example.utils.CubeInDepthTransformation
import com.example.utils.ViewBindingFragment

class OnboardingMainFragment :
    ViewBindingFragment<FragmentOndboardingMainBinding>(FragmentOndboardingMainBinding::inflate),
    OnboardingSkip {

    private val actBar: ActBar?
        get() = activity?.let { it as? ActBar }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actBar?.actBar(true, "")
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = OnboardingAdapter(this)
        binding.wormDotsIndicator.setViewPager2(viewPager)
        viewPager.setPageTransformer(CubeInDepthTransformation())
    }

    override fun onboardingSkip(item: Int) {
        binding.viewPager.currentItem = item
        if (item > 2) {
            findNavController().navigate(
                OnboardingMainFragmentDirections.actionOnboardingMainFragmentToAuthFragment()
            )
        }
    }
}

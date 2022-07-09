package com.example.strava4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.WorkerThread
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.auth.databinding.FragmentAuthBinding
import com.example.strava4.databinding.FragmentMainBinding
import com.example.utils.ActBar
import com.example.utils.ViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint

class MainFragment : ViewBindingFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val actBar: ActBar?
        get() = activity?.let { it as? ActBar }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val host: NavHostFragment? = childFragmentManager
            .findFragmentById(R.id.fragment_app) as NavHostFragment?
        val navController = host?.navController
        if (navController != null) {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id == com.example.activity.R.id.createActivityFragment) {
                    binding.navigationView.visibility = View.GONE
                } else {
                    binding.navigationView.visibility = View.VISIBLE
                }
                if (destination.id == com.example.activity.R.id.activityFragment) {
                    actBar?.actBar(false, getString(com.example.activity.R.string.activities))
                }
                if (destination.id == com.example.profile.R.id.profileFragment) {
                    actBar?.actBar(false, getString(com.example.profile.R.string.profile))
                }
                if (destination.id == com.example.profile.R.id.shareFragment) {
                    actBar?.actBar(false, getString(com.example.profile.R.string.share))
                }
            }
            binding.navigationView.setupWithNavController(navController)
        }
    }
}
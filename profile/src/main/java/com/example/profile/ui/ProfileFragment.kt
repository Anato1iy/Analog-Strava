package com.example.profile.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.navigations.NavigationFlow
import com.example.navigations.ToFlowNavigatable
import com.example.profile.R
import com.example.profile.databinding.FragmentProfileBinding
import com.example.utils.ViewBindingFragment
import com.example.utils.NotificationChannels
import com.example.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : ViewBindingFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProfileAthlete()
        observeViewModel()
        binding.buttonLogout.setOnClickListener {
            CustomDialog().show(childFragmentManager, "MyCustomDialog")
        }
        weightDialog()
        binding.buttonShare.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToShareFragment())
        }
        if (viewModel.isPushNotif()) {
            showNotification()
        }
    }

    private fun showNotification(){
        val notification = NotificationCompat.Builder(requireContext(), NotificationChannels.MESSAGES_LOW_CHANEL_ID)
            .setContentTitle("ASCENT")
            .setContentText(getString(R.string.no_activities))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_notifications)
            .build()

        NotificationManagerCompat.from(requireContext())
            .notify(NOTIFICATION_ID, notification)
    }

    @SuppressLint("SetTextI18n")
    private fun observeViewModel() {
        viewModel.isLogout.observe(viewLifecycleOwner) {
            if (it) {
                (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.AuthFrag)
            }
        }
        viewModel.athlete.observe(viewLifecycleOwner) { athlete ->
            binding.textViewName.text = "${athlete.firstname} ${athlete.lastname}"
            binding.textViewGender.text = athlete.sex
            binding.textViewCountry.text = athlete.country
            binding.textViewWeight.text = "${athlete.weight} ${getString(R.string.kg)}"
            binding.textViewFollowers.text = "${athlete.followerCount}"
            binding.textViewFollowing.text = "${athlete.friendCount}"
            Glide.with(this).load(athlete.profile)
                .placeholder(R.drawable.ic_baseline_insert_emoticon)
                .error(R.drawable.ic_baseline_error)
                .into(binding.imageViewAvatar)
        }
    }

    fun logout() {
        viewModel.logout()
    }

    @SuppressLint("SetTextI18n")
    private fun weightDialog() {
        binding.checkBoxSelectWeight.setOnClickListener {
            val arrayWeight = mutableListOf<String>()
            for (i in 20..300) {
                arrayWeight.add("$i ${getString(R.string.kg)}")
            }
            var whichNum = -1
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.select_your_weight))
                .setSingleChoiceItems(arrayWeight.toTypedArray(), -1) { _, which ->
                    whichNum = which
                }
                .setPositiveButton("Ок") { _, _ ->
                    if (whichNum != -1) {
                        val weight = whichNum + 20
                        viewModel.updateWeight(weight)
                        binding.textViewWeight.text = "$weight ${getString(R.string.kg)}"
                    } else {
                        toast(getString(R.string.not_select_weight))
                    }
                }
                .show()
        }
    }

    companion object{
        private const val NOTIFICATION_ID = 12346
    }
}
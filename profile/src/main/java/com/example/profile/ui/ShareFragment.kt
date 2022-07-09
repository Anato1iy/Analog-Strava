package com.example.profile.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profile.R
import com.example.profile.databinding.FragmentShareBinding
import com.example.profile.ui.adapterShare.ShareAdapter
import com.example.utils.ViewBindingFragment
import com.example.utils.toast
import com.google.android.material.snackbar.Snackbar
import com.example.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShareFragment : ViewBindingFragment<FragmentShareBinding>(FragmentShareBinding::inflate) {

    var fragmentAdapter: ShareAdapter by autoCleared()
    private val viewModel: ShareViewModel by viewModels()

    private val requestPermissionContact = registerForActivityResult(
        RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            binding.textViewAllowAscent.isVisible = false
            binding.buttonAllow.isVisible = false
            showContacts()
        } else {
            binding.textViewAllowAscent.isVisible = true
            binding.buttonAllow.isVisible = true
            onErrorSnackBar()
        }
    }

    private val requestPermissionSms = registerForActivityResult(
        RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            toast("No Send SMS")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAllow.setOnClickListener {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this.requireActivity(),
                    Manifest.permission.READ_CONTACTS
                )
            ) {
                showContactWithPermissionCheck()
            } else {
                val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri: Uri = Uri.fromParts("package", requireContext().packageName, null)
                settingsIntent.data = uri
                startActivity(settingsIntent)
            }
        }

        binding.itemsSwipeToRefresh.setOnRefreshListener {
            showContacts()
            binding.itemsSwipeToRefresh.isRefreshing = false
        }

        initList()
        showContacts()
        bindViewModel()
    }

    private fun showContacts() {
        if (Build.VERSION.SDK_INT >= 24) {
            if (showContactWithPermissionCheck()) {
                viewModel.loadList()
            }
        } else {
            viewModel.loadList()
        }
    }

    private fun initList() = with(binding.ContactList) {
        fragmentAdapter = ShareAdapter(onItemClicked = {
            if (Build.VERSION.SDK_INT >= 24) {
                if (sendSmsWithPermissionCheck()) {
                    sendSms(it)
                }
            } else {
                sendSms(it)
            }
        })
        adapter = fragmentAdapter
        layoutManager = LinearLayoutManager(requireContext())
        setHasFixedSize(true)
    }

    private fun bindViewModel() {
        viewModel.contactsList.observe(viewLifecycleOwner) {
            fragmentAdapter.items = it
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBarShare.isVisible = it
        }
    }

    private fun showContactWithPermissionCheck(): Boolean {
        val isContactPermissionGranted = ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
        if (isContactPermissionGranted) {
            binding.textViewAllowAscent.isVisible = false
            binding.buttonAllow.isVisible = false
            return true
        } else {
            requestReadContactsPermission()
        }
        return false
    }

    private fun sendSmsWithPermissionCheck(): Boolean {
        val isSmsPermissionGranted = ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.SEND_SMS
        ) == PackageManager.PERMISSION_GRANTED
        if (isSmsPermissionGranted) {
            return true
        } else {
            requestSendSMSPermission()
        }
        return false
    }

    private fun sendSms(numberTelephone: String) {
        viewModel.getIdAthlete()
        viewModel.idAthlete.observe(viewLifecycleOwner) { idAthlete ->
            if (idAthlete.isNotBlank()) {
                val sms = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$numberTelephone"))
                sms.putExtra(
                    "sms_body",
                    "${resources.getText(R.string.i_have_strava)} : https://www.strava.com/athletes/$idAthlete"
                )
                startActivity(sms)
            }
        }
    }

    private fun onErrorSnackBar() {
        val snackbar = Snackbar.make(binding.snackbarLayoutShare, "", 10000)
        val customSnackView = layoutInflater.inflate(R.layout.custom_snackbar_share, null)
        snackbar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
        val layout = customSnackView.findViewById<View>(R.id.layoutCustomSnackBar)
        layout.backgroundTintList =
            ContextCompat.getColorStateList(requireContext(), R.color.ping_error)
        val image = customSnackView.findViewById<ImageView>(R.id.imageViewError)
        image.setBackgroundResource(R.drawable.ic_outline_report_problem)
        val textError = customSnackView.findViewById<TextView>(R.id.textViewError)
        textError.text = resources.getText(R.string.error_share)
        textError.setTextColor(ContextCompat.getColor(requireContext(),R.color.red_text_error))
        val buttonClose = customSnackView.findViewById<ImageView>(R.id.ivCloseErrorActivities)
        buttonClose.setOnClickListener {
            snackbar.dismiss()
        }
        snackbarLayout.addView(customSnackView, 0)
        snackbar.show()
    }

    private fun requestReadContactsPermission() {
        requestPermissionContact.launch(Manifest.permission.READ_CONTACTS)
    }

    private fun requestSendSMSPermission() {
        requestPermissionSms.launch(Manifest.permission.SEND_SMS)
    }
}
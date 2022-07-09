package com.example.activity.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.activity.R
import com.example.activity.databinding.FragmentActivityBinding
import com.example.activity.ui.adapter_activity.ActivityAdapter
import com.example.utils.ViewBindingFragment
import com.google.android.material.snackbar.Snackbar
import com.example.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator

@AndroidEntryPoint
class ActivityFragment: ViewBindingFragment<FragmentActivityBinding>(FragmentActivityBinding::inflate) {

    var fragmentAdapter: ActivityAdapter by autoCleared()
    private val viewModel: ActivityViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getListActivity()
        binding.addFabActivities.setOnClickListener {
            findNavController().navigate(ActivityFragmentDirections.actionActivityFragmentToCreateActivityFragment())
        }
        binding.itemsSwipeToRefresh.setOnRefreshListener {
            viewModel.getListActivity()
            binding.itemsSwipeToRefresh.isRefreshing = false
        }

        initList()
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.activityList.observe(viewLifecycleOwner) {
            fragmentAdapter.items = it
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBarListActivities.isVisible = it
        }
        viewModel.isErrorActivity.observe(viewLifecycleOwner) { error ->
            if (error) {
                onErrorSnackBar()
            }
        }
        viewModel.isCache.observe(viewLifecycleOwner) { isCash ->
            if (isCash) {
                onCashSnackBar()
            }
        }
    }

    private fun initList() = with(binding.activitiesList) {
        fragmentAdapter = ActivityAdapter()
        adapter = fragmentAdapter
        layoutManager = LinearLayoutManager(requireContext())
        setHasFixedSize(true)
        itemAnimator = SlideInLeftAnimator()
    }

    private fun onErrorSnackBar() {
        val snackbar = Snackbar.make(binding.snackbarLayout, "", 10000)
        val customSnackView = layoutInflater.inflate(R.layout.custom_snackbar, null)
        snackbar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
        val layout = customSnackView.findViewById<View>(R.id.layoutCustomSnackBar)
        layout.backgroundTintList =
            ContextCompat.getColorStateList(requireContext(), R.color.ping_error)
        val image = customSnackView.findViewById<ImageView>(R.id.imageViewError)
        image.setBackgroundResource(R.drawable.ic_outline_report_problem)
        val textError = customSnackView.findViewById<TextView>(R.id.textViewError)
        textError.text = resources.getText(R.string.error_activities)
        textError.setTextColor(ContextCompat.getColor(requireContext(),R.color.red_text_error))
        val buttonRetry = customSnackView.findViewById<TextView>(R.id.textViewRetry)
        buttonRetry.setOnClickListener {
            viewModel.getListActivity()
        }
        val buttonClose = customSnackView.findViewById<ImageView>(R.id.ivCloseErrorActivities)
        buttonClose.setOnClickListener {
            snackbar.dismiss()
        }
        snackbarLayout.addView(customSnackView, 0)
        snackbar.show()
    }

    private fun onCashSnackBar() {
        val snackbar = Snackbar.make(binding.snackbarLayout, "", 10000)
        val customSnackView = layoutInflater.inflate(R.layout.custom_snackbar, null)
        snackbar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
        val layout = customSnackView.findViewById<View>(R.id.layoutCustomSnackBar)
        layout.backgroundTintList =
            ContextCompat.getColorStateList(requireContext(), R.color.orange_cash)
        val image = customSnackView.findViewById<ImageView>(R.id.imageViewError)
        image.setBackgroundResource(R.drawable.ic_baseline_notifications)
        val textError = customSnackView.findViewById<TextView>(R.id.textViewError)
        textError.text = resources.getText(R.string.use_cache)
        textError.setTextColor(ContextCompat.getColor(requireContext(),R.color.red_text_error))
        val buttonRetry = customSnackView.findViewById<TextView>(R.id.textViewRetry)
        buttonRetry.isGone = true
        val buttonClose = customSnackView.findViewById<ImageView>(R.id.ivCloseErrorActivities)
        buttonClose.setOnClickListener {
            snackbar.dismiss()
        }
        snackbarLayout.addView(customSnackView, 0)
        snackbar.show()
    }
}
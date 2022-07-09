package com.example.activity.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.activity.R
import com.example.activity.databinding.FragmentCreateActivityBinding
import com.example.activity.date.Activities
import com.example.activity.ui.adapter_activity.CustomAdapter
import com.example.utils.ViewBindingFragment
import com.example.utils.ActBar
import com.example.utils.toast
import com.example.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CreateActivityFragment :
    ViewBindingFragment<FragmentCreateActivityBinding>(FragmentCreateActivityBinding::inflate) {

    private val viewModel: CreateActivityViewModel by viewModels()
    private var customAdapter: CustomAdapter by autoCleared()
    private val actBar: ActBar?
        get() = activity?.let { it as? ActBar }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actBar?.actBar(true, "")
        turnOnSpinner()

        binding.textViewDateActivity.setOnClickListener {
            initTimePicker()
        }

        binding.buttonAdd.setOnClickListener {
            val nameActivity = binding.editTextNameActivity.text.toString()
            val typeActivity = binding.spinnerType.selectedItem.toString()
            val dateActivity = binding.textViewDateActivity.text
            val timeActivity = binding.editTextTimeActivity.text.toString()
            val distanceActivity = binding.editTextDistanceActivity.text.toString()
            val descriptionsActivity = binding.editTextDescriptionsActivity.text.toString()

            var isNotEmpty = true
            if (nameActivity.isEmpty()) {
                isNotEmpty = false
                binding.editTextNameActivity.background = shapeRed()
            }
            if (!CustomAdapter.flag_) {
                isNotEmpty = false
                binding.spinnerType.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.spinner_arrow_red)
            }
            if (dateActivity == getString(R.string.date)) {
                isNotEmpty = false
                binding.textViewDateActivity.background = shapeRed()
            }
            if (timeActivity.isEmpty()) {
                isNotEmpty = false
                binding.editTextTimeActivity.background = shapeRed()
            }
            if (distanceActivity.isEmpty()) {
                isNotEmpty = false
                binding.editTextDistanceActivity.background = shapeRed()
            }

            if (isNotEmpty) {

                viewModel.addActivities(
                    Activities(
                        nameActivity,
                        typeActivity,
                        viewModel.date.value,
                        timeActivity.toInt(),
                        distanceActivity.toFloat(),
                        descriptionsActivity
                    )
                )
                findNavController().navigate(CreateActivityFragmentDirections.actionCreateActivityFragmentToActivityFragment())
                onDestroy()
            } else {
                toast("fill in the fields")
            }
        }
    }

    private fun shapeRed(): Drawable? {
        return ContextCompat.getDrawable(requireContext(), R.drawable.custom_layout_shape_red)
    }

    private fun initTimePicker() {
        val cal = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                TimePickerDialog(
                    requireContext(),
                    { _, hour, minute ->
                        cal.set(Calendar.HOUR_OF_DAY, year)
                        cal.set(Calendar.HOUR_OF_DAY, month)
                        cal.set(Calendar.HOUR_OF_DAY, day)
                        cal.set(Calendar.HOUR_OF_DAY, hour)
                        cal.set(Calendar.MINUTE, minute)
                        viewModel.saveDate(cal)
                        binding.textViewDateActivity.text =
                            SimpleDateFormat("dd.MM.yyyy HH:mm").format(cal.time)

                    },
                    cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true
                )
                    .show()
            },
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
        )
            .show()
    }


    private fun turnOnSpinner() {

        val typeActivity = mutableListOf<String>()
        typeActivity.add("Run")
        typeActivity.add("Ride")
        typeActivity.add("Swim")
        customAdapter = CustomAdapter(
            requireContext(),
            R.layout.item_simple_spinner,
            typeActivity.toTypedArray()
        )
        customAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerType.adapter = customAdapter
        binding.spinnerType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    CustomAdapter.flag = true
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        CustomAdapter.flag = false
    }
}
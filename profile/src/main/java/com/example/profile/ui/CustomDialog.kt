package com.example.profile.ui

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.profile.R

class CustomDialog : DialogFragment() {
    private lateinit var buttonYes: Button
    private lateinit var buttonNo: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_in_logout, null)
            builder.setView(view)
            buttonYes = view.findViewById(R.id.buttonDialogYes)
            buttonNo = view.findViewById(R.id.buttonDialogNo)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onResume() {
        super.onResume()
        val dialog = dialog as AlertDialog?
        if (dialog != null) {
            buttonYes.setOnClickListener {
                (parentFragment as ProfileFragment).logout()
                dialog.dismiss()
            }
            buttonNo.setOnClickListener {
                dialog.dismiss()
            }
        }
    }
}

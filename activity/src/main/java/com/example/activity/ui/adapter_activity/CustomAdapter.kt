package com.example.activity.ui.adapter_activity

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CustomAdapter(
    context_: Context,
    private val textViewResourceId: Int,
    private val objects: Array<String>
) : ArrayAdapter<Any?>(context_, textViewResourceId, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        flag_ = flag
        var convertViewCA = convertView
        if (convertViewCA == null) convertViewCA = View.inflate(
            context,
            textViewResourceId, null
        )
        if (flag) {
            val tv = convertViewCA as TextView?
            tv!!.text = objects[position]
        }
        return convertViewCA!!
    }

    companion object {
        var flag = false
        var flag_ = false
    }
}
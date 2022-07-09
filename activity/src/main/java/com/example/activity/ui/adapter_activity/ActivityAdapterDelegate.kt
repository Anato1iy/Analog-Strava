package com.example.activity.ui.adapter_activity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.activity.R
import com.example.activity.databinding.ItemActivityBinding
import com.example.api.data.ListActivitiesWithProfile


import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ActivityAdapterDelegate() :
    AbsListItemAdapterDelegate<ListActivitiesWithProfile, ListActivitiesWithProfile, ActivityAdapterDelegate.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(
            ItemActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun isForViewType(
        item: ListActivitiesWithProfile,
        items: MutableList<ListActivitiesWithProfile>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onBindViewHolder(
        item: ListActivitiesWithProfile,
        holder: Holder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class Holder(
        private val itemBinding: ItemActivityBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(activity: ListActivitiesWithProfile) {
            itemBinding.textViewNameActivity.text = activity.name
            itemBinding.textViewDistanceActivity.text = activity.distance
            itemBinding.textViewElevationsActivity.text = activity.elevationGain
            itemBinding.textViewTimeActivity.text = activity.time
            val dateTime = ZonedDateTime.parse(activity.startDate)
            val dateString = dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
            itemBinding.textViewTimeListActivity.text = dateString
            itemBinding.textViewNameAthlete.text = "${activity.firstName} ${activity.lastName}"
            Glide.with(itemView).load(activity.profile)
                .placeholder(R.drawable.ic_baseline_insert_emoticon)
                .error(R.drawable.ic_baseline_insert_emoticon)
                .into(itemBinding.imageViewAthlete)
        }
    }
}

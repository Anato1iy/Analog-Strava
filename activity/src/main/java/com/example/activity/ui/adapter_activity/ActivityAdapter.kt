package com.example.activity.ui.adapter_activity

import androidx.recyclerview.widget.DiffUtil
import com.example.api.data.ListActivitiesWithProfile
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ActivityAdapter() : AsyncListDifferDelegationAdapter<ListActivitiesWithProfile>(
    EntityDiffUtilCallback()
) {
    init {
        delegatesManager.addDelegate(ActivityAdapterDelegate())
    }

    class EntityDiffUtilCallback : DiffUtil.ItemCallback<ListActivitiesWithProfile>() {
        override fun areItemsTheSame(
            oldItem: ListActivitiesWithProfile,
            newItem: ListActivitiesWithProfile
        ): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(
            oldItem: ListActivitiesWithProfile,
            newItem: ListActivitiesWithProfile
        ): Boolean {
            return oldItem == newItem
        }
    }
}
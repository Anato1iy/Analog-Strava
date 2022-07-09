package com.example.profile.ui.adapterShare

import androidx.recyclerview.widget.DiffUtil
import com.example.api.data.Contact
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ShareAdapter (onItemClicked:(numberTelephone:String) -> Unit) :
    AsyncListDifferDelegationAdapter<Contact>(EntityDiffUtilCallback()) {
    init {
        delegatesManager.addDelegate(ShareAdapterDelegate(onItemClicked))
    }

    class EntityDiffUtilCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }
    }
}
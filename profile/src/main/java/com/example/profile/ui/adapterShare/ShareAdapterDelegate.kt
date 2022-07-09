package com.example.profile.ui.adapterShare

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.profile.R
import com.example.api.data.Contact
import com.example.profile.databinding.ItemContactBinding
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class ShareAdapterDelegate (private val onItemClick: (numberTelephone: String) -> Unit):
    AbsListItemAdapterDelegate<Contact, Contact, ShareAdapterDelegate.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(
            ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClick
        )
    }

    override fun isForViewType(
        item: Contact,
        items: MutableList<Contact>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onBindViewHolder(
        item: Contact,
        holder: Holder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class Holder(
        private val itemBinding: ItemContactBinding,
        onItemClick: (numberTelephone: String) -> Unit
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemBinding.root.setOnClickListener {
                onItemClick(itemBinding.textViewPhoneContact.text.toString())
            }
        }

        fun bind(contact: Contact) {
            itemBinding.textViewNameContact.text = contact.name

            var phones = ""
            contact.telephone.forEach {
                phones = "$phones    $it"
            }
            itemBinding.textViewPhoneContact.text = phones
            Glide.with(itemView).load(contact.photo)
                .placeholder(R.drawable.ic_baseline_insert_emoticon)
                .error(R.drawable.ic_baseline_insert_emoticon)
                .into(itemBinding.imageViewAvatar)
        }
    }
}

package com.binarfp.airtrip.presentation.ui.buyer

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.ItemNotifBinding
import com.binarfp.airtrip.model.Notif


class NotificationAdapter(private val itemClick : (Notif)->Unit) :
    RecyclerView.Adapter<NotificationAdapter.ItemViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<Notif>(){
        override fun areItemsTheSame(oldItem: Notif, newItem: Notif): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Notif, newItem: Notif): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitData (value: List<Notif>)= differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType : Int): ItemViewHolder {
        val binding =
            ItemNotifBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  ItemViewHolder(binding)
    }
    class ItemViewHolder(val binding: ItemNotifBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        with(holder){
            with(differ.currentList[position]){
                if (this.hasRead.not()){
                    binding.cvResult.setBackgroundResource(R.color.light_blue)
                }
                binding.tvMessage.text = this.message+" dengan ticket id : "+this.ticketId
                val date = this.createdAt.split("T")[0]
                val time = this.createdAt.split("T")[1]
                binding.tvTime.text = "$date $time"
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}
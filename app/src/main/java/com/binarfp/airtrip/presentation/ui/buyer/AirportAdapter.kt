package com.binarfp.airtrip.presentation.ui.buyer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binarfp.airtrip.databinding.ItemAirportBinding
import com.binarfp.airtrip.model.DataAirport


class AirportAdapter(private val itemClick : (DataAirport)->Unit) :
    RecyclerView.Adapter<AirportAdapter.ItemViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<DataAirport>(){
        override fun areItemsTheSame(oldItem: DataAirport, newItem: DataAirport): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: DataAirport, newItem: DataAirport): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitData (value: List<DataAirport>)= differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType : Int): ItemViewHolder {
        val binding =
            ItemAirportBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  ItemViewHolder(binding)
    }
    class ItemViewHolder(val binding: ItemAirportBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        with(holder){
            with(differ.currentList[position]){
                itemView.setOnClickListener { itemClick(this) }
                binding.iata.text = this.iata
                binding.nameAirport.text = this.name
                binding.addressAirport.text = this.address
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

}
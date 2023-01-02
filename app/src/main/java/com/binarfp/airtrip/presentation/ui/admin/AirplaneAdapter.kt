package com.binarfp.airtrip.presentation.ui.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.ItemAirplaneBinding
import com.binarfp.airtrip.model.Airplane
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class AirplaneAdapter(private val itemClick : (Airplane)->Unit, private val itemClick2 : (Airplane)->Unit,private val asal:String) :
    RecyclerView.Adapter<AirplaneAdapter.ItemViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<Airplane>(){
        override fun areItemsTheSame(oldItem: Airplane, newItem: Airplane): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Airplane, newItem: Airplane): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitData (value: List<Airplane>)= differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType : Int): ItemViewHolder {
        val binding =
            ItemAirplaneBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  ItemViewHolder(binding)
    }
    class ItemViewHolder(val binding: ItemAirplaneBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        with(holder){
            with(differ.currentList[position]){
                binding.btnSelect.setOnClickListener { itemClick(this) }
                binding.tvModelNumberSearch.text = this.modelNumber
                binding.tvManufactureSearch.text = this.manufacture
                binding.tvCapacitySearch.text = this.capacity.toString()
                binding.tvId.text = this.id.toString()
                Glide.with(binding.imageView7)
                    .load(this.image)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.ic_airplane_240)
                            .error(R.drawable.ic_airplane_240))
                    .into(binding.imageView7)
                if (asal=="addairplane"){
                    binding.layoutBtn.visibility = View.VISIBLE
                    binding.btnSelect.visibility = View.GONE
                    binding.btnUpdateFlightAdmin.setOnClickListener { itemClick(this) }
                    binding.btnDeleteFlightAdmin.setOnClickListener { itemClick2(this) }
                }
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}
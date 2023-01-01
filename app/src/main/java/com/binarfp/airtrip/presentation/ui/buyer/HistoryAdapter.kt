package com.binarfp.airtrip.presentation.ui.buyer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binarfp.airtrip.databinding.ItemHistoryBinding
import com.binarfp.airtrip.model.HistoryData

class HistoryAdapter(private val itemClick : (HistoryData)->Unit,private val itemClickpulang : (HistoryData)->Unit) :
    RecyclerView.Adapter<HistoryAdapter.ItemViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<HistoryData>(){
        override fun areItemsTheSame(oldItem: HistoryData, newItem: HistoryData): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: HistoryData, newItem: HistoryData): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitData (value: List<HistoryData>)= differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType : Int): ItemViewHolder {
        val binding =
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  ItemViewHolder(binding)
    }
    class ItemViewHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        with(holder){
            with(differ.currentList[position]){
                if (this.flightType?.lowercase() == "roundtrip"){
                    binding.btnBoardPulang.visibility = View.VISIBLE
                }
                binding.tvPassengerName.text = this.passenger?.name
                binding.tvFlightType.text = this.flightType
                binding.tvTotalPrice.text = "Rp${this.totalPrice}"
                val createdate = this.createdAt?.split("T")!![0]
                val createtime = this.createdAt?.split("T")!![1]
                binding.tvCreate.text = "$createdate $createtime"
                binding.tvInvoice.text = this.invoiceNumber
                binding.btnBoardPergi.setOnClickListener { itemClick(this) }
                binding.btnBoardPulang.setOnClickListener { itemClickpulang(this) }
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}
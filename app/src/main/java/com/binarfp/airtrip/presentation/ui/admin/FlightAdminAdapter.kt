package com.binarfp.airtrip.presentation.ui.admin

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binarfp.airtrip.databinding.ItemSearchBinding
import com.binarfp.airtrip.model.Flight
import com.binarfp.airtrip.presentation.Utils

class FlightAdminAdapter(private val itemClick : (Flight)->Unit, private val itemClick2 : (Flight)->Unit) :
    RecyclerView.Adapter<FlightAdminAdapter.ItemViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<Flight>(){
        override fun areItemsTheSame(oldItem: Flight, newItem: Flight): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Flight, newItem: Flight): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitData (value: List<Flight>)= differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType : Int): ItemViewHolder {
        val binding =
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  ItemViewHolder(binding)
    }
    class ItemViewHolder(val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        with(holder){
            with(differ.currentList[position]){
                val date = this.departure?.split("T")?.get(0)
                val time = this.departure?.split("T")!![1].substring(0,8)
                val date2 = this.arrival?.split("T")?.get(0)
                val time2 = this.arrival?.split("T")!![1].substring(0,8)
                binding.tvTimeDeparture.text = time
                binding.tvTimeArrived.text = time2
                val duration = Utils.duration(time,time2)
                binding.tvDuration.text = duration
                Log.e("duration","$duration"+time+time2)
                binding.tvPrice.text = "Rp."+this.price.toString()
                binding.tvPlaceDeparture.text = this.fromAirport?.iata
                binding.tvDetailPlaceDeparture.text = this.fromAirport?.name
                binding.tvPlaceArrived.text = this.toAirport?.iata
                binding.tvDetailPlaceArrived.text = this.toAirport?.name
                binding.tvSeatClass.text = this.flightClass+" Class"
                binding.tvDateResult1.text = date
                binding.tvDateResult2.text = date2
                binding.buttonBook.visibility = View.GONE
                binding.layoutBtn.visibility = View.VISIBLE
                binding.btnUpdateFlightAdmin.setOnClickListener { itemClick(this) }
                binding.btnDeleteFlightAdmin.setOnClickListener { itemClick2(this) }
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}
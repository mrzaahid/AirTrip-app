package com.binarfp.airtrip.presentation.ui.buyer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.FragmentDetailBinding
import com.binarfp.airtrip.model.Flight
import com.binarfp.airtrip.presentation.MainViewModel
import com.binarfp.airtrip.presentation.Utils
import com.zaahid.challenge6.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding : FragmentDetailBinding
    private val mainViewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val flight = arguments?.getSerializable("flight") as Flight
        val date = flight.departure?.split("T")?.get(0)
        val time = flight.departure?.split("T")!![1].substring(0,8)
        val date2 = flight.arrival?.split("T")?.get(0)
        val time2 = flight.arrival?.split("T")!![1].substring(0,8)
        binding.tvTimeDeparture.text = time
        binding.tvTimeArrived.text = time2
        binding.tvDateDeparture.text = date
        binding.tvDateArrival.text = date2
        binding.tvPlaceDeparture.text = flight.fromAirport?.iata
        binding.tvDetailPlaceDeparture.text = flight.fromAirport?.address
        binding.tvAirportDepart.text = flight.fromAirport?.name
        binding.tvPlaceArrived.text = flight.toAirport?.iata
        binding.tvDetailPlaceArrived.text = flight.toAirport?.address
        binding.tvAiportArrive.text = flight.toAirport?.name
        val duration = Utils.duration(time,time2)
        binding.tvDuration.text = duration
        binding.tvPrice.text= flight.price.toString()
        var totalPrice = flight.price
        if (arguments?.getString("round")=="round"){
            initReturn()
            val flight2 = requireArguments().getSerializable("flight2") as Flight
            val dateReturn = flight.departure.split("T")[0]
            val timeReturn = flight.departure.split("T")[1].substring(0,8)
            val date2Return = flight.arrival.split("T")[0]
            val time2Return = flight.arrival.split("T")[1].substring(0,8)
            binding.tvTimeDeparture2.text = timeReturn
            binding.tvTimeArrived2.text = time2Return
            binding.tvDateDeparture2.text = dateReturn
            binding.tvDateArrival2.text = date2Return
            binding.tvPlaceDeparture2.text = flight2.fromAirport?.iata
            binding.tvDetailPlaceDeparture2.text = flight2.fromAirport?.address
            binding.tvAirportDepart2.text = flight2.fromAirport?.name
            binding.tvPlaceArrived2.text = flight2.toAirport?.iata
            binding.tvDetailPlaceArrived2.text = flight2.toAirport?.address
            binding.tvAiportArrive2.text = flight2.toAirport?.name
            val duration2 = Utils.duration(time,time2)
            binding.tvDuration2.text = duration2
            binding.tvPrice2.text= flight2.price.toString()
            if (totalPrice != null) {
                totalPrice += flight2.price!!
                Log.e("total",totalPrice.toString())
            }
        }
        binding.tvTotalOrice
        binding.btnConfirm.setOnClickListener {
            Log.e("flightid", flight.id.toString())
            mainViewModel.getAccesToken().observe(viewLifecycleOwner) {token->
                val jsonObject = JSONObject()
                if (arguments?.getString("asal")=="home"){
                    jsonObject.put("flightId2", null)
                    jsonObject.put("flightType","oneway")
                }
                if (arguments?.getString("asal")=="round"){
                    jsonObject.put("flightId2", null)
                    jsonObject.put("flightType","roundtrip")
                }
                jsonObject.put("flightId",flight.id)
                val jsonObjectString =jsonObject.toString()
                val requestBody =jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
                mainViewModel.createTicket(token,requestBody)
                Log.e("ticket",requestBody.toString())
                mainViewModel.ticket.observe(viewLifecycleOwner){
                    if (it is Resource.Success){
                        val bundle = Bundle()
                        val x = it.payload
                        bundle.putSerializable("ticket",x)
                        findNavController().navigate(R.id.action_detailFragment_to_ticketFragment,bundle)
                    }
                }
            }
        }
    }
    private fun initReturn(){

    }

}
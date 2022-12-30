package com.binarfp.airtrip.presentation.ui.buyer

import android.os.Bundle
import android.text.format.DateUtils
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
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.wait
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
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
        val x = arguments?.getSerializable("flight") as Flight
        val date = x.departure?.split("T")?.get(0)
        val time = x.departure?.split("T")!![1].substring(0,5)
        val date2 = x.arrival?.split("T")?.get(0)
        val time2 = x.arrival?.split("T")!![1].substring(0,5)
        binding.tvTimeDeparture.text = time
        binding.tvTimeArrived.text = time2
        binding.tvDateDeparture.text = date
        binding.tvDateArrival.text = date2
        binding.tvPlaceDeparture.text = x.fromAirport?.iata
        binding.tvDetailPlaceDeparture.text = x.fromAirport?.address
        binding.tvAirportDepart.text = x.fromAirport?.name
        binding.tvPlaceArrived.text = x.toAirport?.iata
        binding.tvDetailPlaceArrived.text = x.toAirport?.address
        binding.tvAiportArrive.text = x.toAirport?.name
        val duration = duration(time,time2)
        binding.tvDuration.text = duration
        binding.tvPrice.text= x.price.toString()
        binding.btnConfirm.setOnClickListener {
            Log.e("flightid", x.id.toString())
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
                jsonObject.put("flightId",x.id)
                val jsonObjectString =jsonObject.toString()
                val requestBody =jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
                mainViewModel.createTicket("Bearer $token",requestBody)
                Log.e("ticket",requestBody.toString())
                mainViewModel.ticket.observe(viewLifecycleOwner){
                    val bundle = Bundle()
                    bundle.putSerializable("ticket",it)
                    Log.e("ticket",it.toString())
                    findNavController().navigate(R.id.action_detailFragment_to_ticketFragment,bundle)
                }
            }
        }
    }
    fun duration(time1 : String,time2 : String):String{
        val date1 = SimpleDateFormat(time1, Locale.getDefault()).parse(time1)
        val date2 = SimpleDateFormat(time2, Locale.getDefault()).parse(time2)
        val duration = DateUtils.getRelativeTimeSpanString(date1.time, date2.time, DateUtils.MINUTE_IN_MILLIS)
        return duration.toString()
    }

}
package com.binarfp.airtrip.presentation.ui.buyer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
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
    private lateinit var binding: FragmentDetailBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val flight = requireArguments().getSerializable("flight") as Flight
        val date = flight.departure?.split("T")?.get(0)
        val time = flight.departure?.split("T")!![1].substring(0, 5)
        val date2 = flight.arrival?.split("T")?.get(0)
        val time2 = flight.arrival?.split("T")!![1].substring(0, 5)
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
        val duration = Utils.duration(time, time2)
        binding.tvDuration.text = duration
        binding.tvPrice.text = flight.price.toString()
        var totalPrice = flight.price
        var flight2 = Flight(null, "", "", "", "", "", "", null, -1, -1, null, null, -1, -1)
        if (arguments?.getString("asal") == "round") {
            binding.layoutReturn.visibility = View.VISIBLE
            flight2 = requireArguments().getSerializable("flight2") as Flight
            val dateReturn = flight2.departure?.split("T")?.get(0)
            val timeReturn = flight2.departure?.split("T")?.get(1)?.substring(0, 5)
            val date2Return = flight2.arrival?.split("T")!![0]
            val time2Return = flight2.arrival!!.split("T")[1].substring(0, 5)
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
            val duration2 = Utils.duration(time, time2)
            binding.tvDuration2.text = duration2
            binding.tvPrice2.text = flight2.price.toString()
            if (totalPrice != null) {
                totalPrice += flight2.price!!
                Log.e("total", totalPrice.toString())
            }
        }
        binding.tvTotalOrice.text = "Rp.$totalPrice"
        var z = 0
        mainViewModel.getAccesToken().observe(viewLifecycleOwner){
            mainViewModel.getProfile(it)
        }
        mainViewModel.responseUser.observe(viewLifecycleOwner) {
            if (it is Resource.Success){
                Log.e("user",it.payload?.data.toString())
                z = it.payload?.data!!.saldo
                binding.tvSaldoResult.text = "Rp.$z"
            }
        }
        binding.btnConfirm.setOnClickListener {
            Log.e("flightid", flight.id.toString())
            mainViewModel.responseUser.observe(viewLifecycleOwner) {
                z = it.payload!!.data.saldo
                if (totalPrice!! > z) {
                    AlertDialog.Builder(requireContext())
                        .setMessage("Saldo Anda Kurang Untuk Membeli Penerbangan ini")
                        .setPositiveButton("Home?") { _, _ ->
                            val intent = Intent(context, HomeActivity::class.java)
                            startActivity(intent)
                        }
                        .setNegativeButton("Cancel") { _, _ -> }
                        .show()
                } else {
                    mainViewModel.getAccesToken().observe(viewLifecycleOwner) { token ->
                        val jsonObject = JSONObject()
                        if (arguments?.getString("asal") == "home") {
                            jsonObject.put("flightId2", null)
                            jsonObject.put("flightType", "oneway")
                        }
                        if (arguments?.getString("asal") == "round") {
                            jsonObject.put("flightId2", flight2)
                            jsonObject.put("flightType", "roundtrip")
                        }
                        jsonObject.put("flightId", flight.id)
                        val jsonObjectString = jsonObject.toString()
                        val requestBody =
                            jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
                        mainViewModel.createTicket(token, requestBody)
                        Log.e("ticket", requestBody.toString())
                        mainViewModel.ticket.observe(viewLifecycleOwner) {ticket->
                            if (it is Resource.Success) {
                                val bundle = Bundle()
                                val x = ticket.payload
                                Log.e("ticket",x.toString())
                                bundle.putSerializable("ticket", x)
                                findNavController().navigate(
                                    R.id.action_detailFragment_to_ticketFragment,
                                    bundle
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}
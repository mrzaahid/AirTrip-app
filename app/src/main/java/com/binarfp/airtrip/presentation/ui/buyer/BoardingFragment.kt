package com.binarfp.airtrip.presentation.ui.buyer

import android.os.Bundle
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.FragmentBoardingBinding
import com.binarfp.airtrip.model.BoardingPassDetail
import com.binarfp.airtrip.presentation.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

class BoardingFragment : Fragment() {
    private lateinit var binding :FragmentBoardingBinding
    private val mainViewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBoardingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val board = arguments?.getSerializable("boardingpass") as BoardingPassDetail
        binding.tvSeat.text = board.seat
        val flight = board.flight
        binding.tvPlaceDeparture.text = flight?.fromAirport?.iata
        binding.tvDetailPlaceDeparture.text = flight?.fromAirport?.name
        binding.tvPlaceArrived.text = flight?.toAirport?.iata
        binding.tvDetailPlaceArrived.text = flight?.toAirport?.name
        val date = flight?.departure?.split("T")?.get(0)
        val time = flight?.departure?.split("T")!![1].substring(0,5)
        val date2 = flight.arrival?.split("T")?.get(0)
        val time2 = flight.arrival?.split("T")!![1].substring(0,5)
        binding.tvTimeDeparture.text = time
        binding.tvTimeArrived.text = time2
        val duration = duration(time,time2)
        binding.tvDuration.text = duration
        binding.tvPassengerName.text = arguments?.getString("name")
        binding.tvDate.text = date
        binding.tvSeatClass.text = flight.flightClass
        binding.tvSeat.text = board.seat
    }
}
fun duration(time1 : String,time2 : String):String{
    val date1 = SimpleDateFormat(time1, Locale.getDefault()).parse(time1)
    val date2 = SimpleDateFormat(time2, Locale.getDefault()).parse(time2)
    val duration = DateUtils.getRelativeTimeSpanString(date1.time, date2.time, DateUtils.MINUTE_IN_MILLIS)
    return duration.toString()
}
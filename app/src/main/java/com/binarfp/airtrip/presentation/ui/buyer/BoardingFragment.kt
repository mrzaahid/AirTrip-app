package com.binarfp.airtrip.presentation.ui.buyer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.FragmentBoardingBinding
import com.binarfp.airtrip.model.BoardingPassDetail
import com.binarfp.airtrip.presentation.MainViewModel
import com.binarfp.airtrip.presentation.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        val board = requireArguments().getSerializable("boardingpass") as BoardingPassDetail
        binding.tvSeat.text = board.seat
        val flight = board.flight
        binding.tvPlaceDeparture.text = flight?.fromAirport?.iata
        binding.tvDetailPlaceDeparture.text = flight?.fromAirport?.name
        binding.tvPlaceArrived.text = flight?.toAirport?.iata
        binding.tvDetailPlaceArrived.text = flight?.toAirport?.name
        val date = flight?.departure?.split("T")?.get(0)
        val time = flight?.departure?.split("T")!![1].substring(0,8)
        val date2 = flight.arrival?.split("T")?.get(0)
        val time2 = flight.arrival?.split("T")!![1].substring(0,8)
        binding.tvTimeDeparture.text = time
        binding.tvTimeArrived.text = time2
        val duration = Utils.duration(time,time2)
        binding.tvDuration.text = duration
        binding.tvPassengerName.text = arguments?.getString("name")
        binding.tvDate.text = date
        binding.tvSeatClass.text = flight.flightClass
        binding.tvSeat.text = board.seat
        if (flight.airplane!=null){
            binding.tvAirplane.text = flight.airplane?.manufacture +" "+ flight.airplane!!.modelNumber
            binding.tvAirplane.setOnClickListener {
                val airplane = flight.airplane
                val bundle = Bundle()
                bundle.putSerializable("airplane",airplane)
                findNavController().navigate(R.id.action_boardingFragment_to_airplaneFragment,bundle)
            }
        }else{
            binding.tvAirplane.visibility = View.GONE
            binding.textView5.visibility = View.GONE
        }

        if (arguments?.getString("asal")=="history"){
            mainViewModel.getAirports()
            mainViewModel.getAirports.observe(viewLifecycleOwner){
                val result = it.payload?.data?.first{data->
                    data.id == flight.from
                }
                val result2 = it.payload?.data?.first{ data->
                    data.id == flight.to
                }
                binding.tvPlaceDeparture.text = result?.iata
                binding.tvDetailPlaceDeparture.text = result?.name
                binding.tvPlaceArrived.text = result2?.iata
                binding.tvDetailPlaceArrived.text = result2?.name
                binding.tvPassengerName.text = requireArguments().getString("name")
            }
        }
    }
}

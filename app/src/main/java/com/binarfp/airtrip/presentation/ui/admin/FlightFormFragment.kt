package com.binarfp.airtrip.presentation.ui.admin

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.FragmentFlightFormBinding
import com.binarfp.airtrip.model.DataAirport
import com.binarfp.airtrip.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class FlightFormFragment : Fragment() {
    private lateinit var binding: FragmentFlightFormBinding
    private val mainViewModel : MainViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =FragmentFlightFormBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onResume() {
        super.onResume()
        val kelas = listOf("bussiness","first","economy")
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.drop_down_item,kelas)
        binding.autoText.setAdapter(arrayAdapter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val dateSetListener = DatePickerDialog(requireContext(),{ view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR,year)
            cal.set(Calendar.MONTH,monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            val myFormat = "yyyy-MM-dd" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            binding.etDeparture.text = sdf.format(cal.time)
            mainViewModel.setdate1(binding.etDeparture.text.toString())
        }, year, month, day)
        val dateSetListener2 = DatePickerDialog(requireContext(),{ view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR,year)
            cal.set(Calendar.MONTH,monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            val myFormat = "yyyy-MM-dd" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            binding.etArrival.text = sdf.format(cal.time)
            mainViewModel.setdate2(binding.etArrival.text.toString())
        }, year, month, day)
        binding.etDeparture.setOnClickListener {
            dateSetListener.show()
        }
        binding.etArrival.setOnClickListener {
            dateSetListener2.show()
        }
        binding.etDepartureAirport.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id",3)
            mainViewModel.setprice(binding.etPrice.text.toString())
            findNavController().navigate(R.id.action_flightFormFragment_to_searchAirportFragment2,bundle)
        }
        binding.etArrivalAirport.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id",3)
            findNavController().navigate(R.id.action_flightFormFragment_to_searchAirportFragment2,bundle)
        }
        var airport1 : DataAirport? = null
        var airport2 : DataAirport? = null
        if ((arguments?.getInt("id") ?: 0) > 0){
            airport1 = arguments?.getSerializable("airport1") as DataAirport
            binding.etDepartureAirport.setText(airport1.name)
            mainViewModel.setairport1(airport1)
            airport2 = arguments?.getSerializable("airport2") as DataAirport
            binding.etArrivalAirport.setText(airport2.name)
            mainViewModel.setairport2(airport2)
        }
        binding.etAirplaneId.setOnClickListener {
            val bundle=Bundle()
            bundle.putString("asal","formflight")
            findNavController().navigate(R.id.action_flightFormFragment_to_searchAirplaneFragment,bundle)
        }
//        if (requireArguments().getString("asal")=="formflight"){
//        }
        if (mainViewModel.date1 != null){
            binding.etDeparture.text = mainViewModel.date1
        }
        if (mainViewModel.date2!=null){
            binding.etArrival.text = mainViewModel.date2
        }
        if (mainViewModel.flightClass!=null){
            binding.etFlightClassAdmin[mainViewModel.flightClass!!]
        }
        if (mainViewModel.price!=null){
            binding.etPrice.setText(mainViewModel.price.toString())
        }
        if (mainViewModel.airport1!=null){
            binding.etDepartureAirport.setText(mainViewModel.airport1?.name)
        }
        if(mainViewModel.airport2!=null){
            binding.etArrivalAirport.setText(mainViewModel.airport2?.name)
        }
        if(mainViewModel.airplane !=null){
            binding.etAirplaneId.setText(mainViewModel.airplane?.manufacture)
        }
        if (mainViewModel.desc!=null){
            binding.etDescription.setText(mainViewModel.desc)
        }
    }
}
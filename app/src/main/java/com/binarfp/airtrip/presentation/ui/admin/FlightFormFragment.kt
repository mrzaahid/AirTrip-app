package com.binarfp.airtrip.presentation.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.FragmentFlightFormBinding
import com.binarfp.airtrip.model.Airplane
import com.binarfp.airtrip.model.DataAirport
import com.binarfp.airtrip.model.Flight
import com.binarfp.airtrip.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

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

        binding.etDeparture.setOnClickListener {
            mainViewModel.setdate1(binding.etDeparture.text.toString())
        }
        binding.etArrival.setOnClickListener {
            mainViewModel.setdate2(binding.etArrival.text.toString())
        }
        binding.etDepartureAirport.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id",3)
            mainViewModel.setprice(binding.etPrice.text.toString())
            mainViewModel.seflightclass(binding.autoText.text.toString())
            findNavController().navigate(R.id.action_flightFormFragment_to_searchAirportFragment2,bundle)
        }
        binding.etArrivalAirport.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id",3)
            mainViewModel.setprice(binding.etPrice.text.toString())
            mainViewModel.seflightclass(binding.autoText.text.toString())
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
        if (arguments?.getString("asal")=="formflight"){
            val airplane = arguments?.getSerializable("airplane") as Airplane
            mainViewModel.setairplane(airplane)
        }
        if (arguments?.getString("asal")=="update"){
            val flight = arguments?.getSerializable("flight") as Flight
            flight.description?.let { mainViewModel.setdesc(it) }
            mainViewModel.setprice(flight.price.toString())
            flight.airplane?.let { mainViewModel.setairplane(it) }
            flight.departure?.let { mainViewModel.setdate1(it) }
            flight.flightClass?.let { mainViewModel.seflightclass(it) }
            flight.arrival?.let { mainViewModel.setdate2(it) }
            flight.fromAirport?.let { mainViewModel.setairport1(it) }
            flight.toAirport?.let { mainViewModel.setairport2(it) }
            flight.airplane?.let { mainViewModel.setairplane(it) }
            flight.description?.let { mainViewModel.setdesc(it) }
            flight.id?.let { mainViewModel.setid(it) }
            binding.btnCreateFlight.text = "Update Item"
        }
        if (arguments?.getString("tujuan")=="create"){
           mainViewModel.setdesc(null)
            mainViewModel.setprice(null)
            mainViewModel.setairplane(null)
           mainViewModel.setdate1(null)
            mainViewModel.seflightclass(null)
            mainViewModel.setdate2(null)
            mainViewModel.setairport1(null)
            mainViewModel.setairport2(null)
             mainViewModel.setairplane(null)
            mainViewModel.setdesc(null)
             mainViewModel.setid(null)
        }
        if (mainViewModel.date1 != null){
            binding.etDeparture.setText( mainViewModel.date1)
        }
        if (mainViewModel.date2!=null){
            binding.etArrival.setText(mainViewModel.date2)
        }
        if (mainViewModel.price!=null){
            binding.etPrice.setText(mainViewModel.price.toString())
        }
        if (mainViewModel.flightClass!=null){
            binding.autoText.setText(mainViewModel.flightClass!!)
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
        binding.btnCreateFlight.setOnClickListener {
            if (checkForm()){
                if (arguments?.getString("asal")=="update"){
                    val jsonObject = JSONObject()
                    jsonObject.put("departure", binding.etDeparture.text)
                    jsonObject.put("arrival", binding.etArrival.text)
                    jsonObject.put("flight_class",binding.autoText.text)
                    jsonObject.put("price",binding.etPrice.text)
                    jsonObject.put("from", mainViewModel.airport1!!.id)
                    jsonObject.put("to",mainViewModel.airport2!!.id)
                    jsonObject.put("airplane_id",mainViewModel.airplane?.id)
                    jsonObject.put("description",binding.etDescription.text)
                    val jsonObjectString =jsonObject.toString()
                    val requestBody =jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
                    mainViewModel.getAccesToken().observe(viewLifecycleOwner){
                        mainViewModel.id?.let { it1 ->
                            mainViewModel.updateFlight(it,
                                it1,requestBody)
                            findNavController().navigate(R.id.action_flightFormFragment_to_flightFragment)
                        }
                    }
                }else{
                    val jsonObject = JSONObject()
                    jsonObject.put("departure", binding.etDeparture.text)
                    jsonObject.put("arrival", binding.etArrival.text)
                    jsonObject.put("flight_class",binding.autoText.text)
                    jsonObject.put("price",binding.etPrice.text)
                    jsonObject.put("from", mainViewModel.airport1!!.id)
                    jsonObject.put("to",mainViewModel.airport2!!.id)
                    jsonObject.put("airplane_id",mainViewModel.airplane?.id)
                    jsonObject.put("description",binding.etDescription.text)
                    val jsonObjectString =jsonObject.toString()
                    val requestBody =jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
                    mainViewModel.getAccesToken().observe(viewLifecycleOwner){
                        mainViewModel.createFlight(it,requestBody)
                        findNavController().navigate(R.id.action_flightFormFragment_to_flightFragment)
                    }
                }
            }

        }
    }
    private fun checkForm():Boolean{
        var a = true
        if (binding.etDeparture.text.isNullOrEmpty()){
            Toast.makeText(context, "Departure date is empty", Toast.LENGTH_SHORT).show()
            a = false
        }else
        if (binding.etArrival.text.isNullOrEmpty()){
            a = false
            Toast.makeText(context, "Arrival date number is empty", Toast.LENGTH_SHORT).show()
        }else
        if (binding.autoText.text.isNullOrEmpty()){
            a = false
            Toast.makeText(context, "flight Class is empty", Toast.LENGTH_SHORT).show()
        }else
        if(binding.etPrice.text.isNullOrEmpty()){
            a = false
            Toast.makeText(context, "price still empty", Toast.LENGTH_SHORT).show()
        }else
        if(binding.etDepartureAirport.text.isNullOrEmpty()){
            a = false
            Toast.makeText(context, "Airport is empty", Toast.LENGTH_SHORT).show()
        }else
        if (binding.etArrivalAirport.text.isNullOrEmpty()){
            a = false
            Toast.makeText(context, "Airport is empty", Toast.LENGTH_SHORT).show()
        }else
        if(binding.etAirplaneId.text.isNullOrEmpty()){
            a = false
            Toast.makeText(context, "airplane is empty", Toast.LENGTH_SHORT).show()
        }else
        if(binding.etDescription.text.isNullOrEmpty()){
            a = false
            Toast.makeText(context, "description is empty", Toast.LENGTH_SHORT).show()
        }
        return a
    }

}
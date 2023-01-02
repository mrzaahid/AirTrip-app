package com.binarfp.airtrip.presentation.ui.buyer

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.FragmentHomeBinding
import com.binarfp.airtrip.model.DataAirport
import com.binarfp.airtrip.presentation.MainViewModel
import com.google.android.material.badge.BadgeDrawable
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding : FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
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
        mainViewModel.getAccesToken().observe(viewLifecycleOwner){
            if(it.isNullOrEmpty()){

            }else{
                mainViewModel.getHistory(it)
                mainViewModel.history.observe(viewLifecycleOwner){
                    val x = it.payload?.data?.size
                    if (x != null) {
                        if(x>0){
                            historyNotif(binding.bottomNavigationView.getOrCreateBadge(R.id.history),x)
                        }
                    }
                }
                mainViewModel.getNotif(it)
                mainViewModel.responsesNotif.observe(viewLifecycleOwner){
                    val x = it.payload?.data?.size
                    if (x != null) {
                        if(x>0){
                            historyNotif(binding.bottomNavigationView.getOrCreateBadge(R.id.notif),x)
                        }
                    }
                }
            }
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.profile->{
                    findNavController().navigate(R.id.action_homeFragment_to_profileActivity)
                    true
                }
                R.id.notif->{
                    findNavController().navigate(R.id.action_homeFragment_to_notificationFragment)
                    true
                }
                R.id.history->{
                    findNavController().navigate(R.id.action_homeFragment_to_historyFragment)
                    true
                }
                else->{
                    false
                }
            }
        }
        binding.btnRound.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_roundFragment)
        }
        binding.autoFrom.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id",1)
            findNavController().navigate(R.id.action_homeFragment_to_searchAirportFragment,bundle)
        }
        binding.autoTo.setOnClickListener{
            val bundle = Bundle()
            bundle.putInt("id",1)
            findNavController().navigate(R.id.action_homeFragment_to_searchAirportFragment,bundle)
        }
        var airport1 : DataAirport? = null
        var airport2 : DataAirport? = null
        if ((arguments?.getInt("id") ?: 0) > 0){
            airport1 = arguments?.getSerializable("airport1") as DataAirport
            binding.autoFrom.setText(airport1.name)
            airport2 = arguments?.getSerializable("airport2") as DataAirport
            binding.autoTo.setText(airport2.name)
        }

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
            binding.etDate.text = sdf.format(cal.getTime())
        }, year, month, day)
        binding.etDate.setOnClickListener {
            dateSetListener.show()

        }

        binding.btnSearch.setOnClickListener {
            if (cekForm(binding.autoFrom.text.toString(),binding.autoTo.text.toString(),
                    binding.etDate.text.toString(),binding.autoText.text.toString())){
                val bundle = Bundle()
                bundle.putSerializable("airport1",airport1)
                bundle.putSerializable("airport2",airport2)
                bundle.putString("date",binding.etDate.text.toString())
                bundle.putString("kelas",binding.autoText.text.toString())
                bundle.putString("asal","home")
                findNavController().navigate(R.id.action_homeFragment_to_resultFragment,bundle)
            }
        }



    }
    fun cekForm(from:String,to:String,date:String,seatclass:String):Boolean{
        var a = true
        if (from.isEmpty()){
            Toast.makeText(
                requireContext(),
                "your Departure Airport still empty",
                Toast.LENGTH_SHORT
            ).show()
            a  = false
        }else
        if (to.isEmpty()){
            Toast.makeText(requireContext(), "Your Arrival Airport still empty", Toast.LENGTH_SHORT)
                .show()
            a = false
        }else
        if (date.isEmpty()){
            Toast.makeText(requireContext(), "Your Departure Date still empty", Toast.LENGTH_SHORT)
                .show()
            a = false
        }else
        if (seatclass.isEmpty()){
            Toast.makeText(requireContext(), "Your Seat Class still empty", Toast.LENGTH_SHORT)
                .show()
            a = false
        }
        return a
    }
    fun historyNotif(badge: BadgeDrawable,angka:Int?):Boolean{
        var a = false
        if(angka!=null && angka >0) {
            val x = angka
            badge.backgroundColor = ContextCompat.getColor(requireContext(), R.color.grey_base)
            badge.badgeTextColor = ContextCompat.getColor(requireContext(), R.color.white)
            badge.maxCharacterCount = 3
            badge.number = x
            badge.isVisible = true
            a = true
        }
        return a
    }
}

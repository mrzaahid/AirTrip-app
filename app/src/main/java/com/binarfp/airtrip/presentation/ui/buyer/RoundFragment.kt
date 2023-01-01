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
import com.binarfp.airtrip.databinding.FragmentRoundBinding
import com.binarfp.airtrip.model.DataAirport
import com.binarfp.airtrip.presentation.MainViewModel
import com.google.android.material.badge.BadgeDrawable
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
@AndroidEntryPoint
class RoundFragment : Fragment() {
    private lateinit var binding : FragmentRoundBinding
    private val mainViewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRoundBinding.inflate(layoutInflater)
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
                    if(x.toString().isNotEmpty()){
                        historyNotif(binding.bottomNavigationView.getOrCreateBadge(R.id.history),x)
                    }
                }
                mainViewModel.getNotif(it)
                mainViewModel.responsesNotif.observe(viewLifecycleOwner){
                    val x = it.payload?.data?.size
                    if(x.toString().isNotEmpty()){
                        historyNotif(binding.bottomNavigationView.getOrCreateBadge(R.id.notif),x)
                    }
                }
            }
        }
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.profile->{
                    findNavController().navigate(R.id.action_roundFragment_to_profileActivity)
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
        binding.btnOneway.setOnClickListener {
            findNavController().navigate(R.id.action_roundFragment_to_homeFragment)
        }
        binding.autoFrom.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id",2)
            findNavController().navigate(R.id.action_roundFragment_to_searchAirportFragment,bundle)
        }
        binding.autoTo.setOnClickListener{
            val bundle = Bundle()
            bundle.putInt("id",2)
            findNavController().navigate(R.id.action_roundFragment_to_searchAirportFragment,bundle)
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
        }, year, month, day)
        binding.etDate.setOnClickListener {
            dateSetListener.show()
            val myFormat = "yyyy-MM-dd" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            binding.etDate.setText(sdf.format(cal.getTime()))
        }
        binding.etReturn.setOnClickListener {
            dateSetListener.show()
            val myFormat = "yyyy-MM-dd" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            binding.etReturn.setText(sdf.format(cal.getTime()))
        }

        binding.btnSearch.setOnClickListener {
            if (cekForm()){
                val bundle = Bundle()
                bundle.putSerializable("airport1",airport1)
                bundle.putSerializable("airport2",airport2)
                bundle.putString("date",binding.etDate.text.toString())
                bundle.putString("date2", binding.etReturn.text.toString())
                bundle.putString("kelas",binding.autoText.text.toString())
                bundle.putString("asal","round")
                findNavController().navigate(R.id.action_roundFragment_to_resultFragment,bundle)
            }
        }
        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.history)
        badge.backgroundColor = ContextCompat.getColor(requireContext(),R.color.grey_base)
        badge.badgeTextColor = ContextCompat.getColor(requireContext(),R.color.white)
        badge.maxCharacterCount = 2
        badge.number = 10
        badge.isVisible = true
    }
    fun cekForm():Boolean{
        var a = true
        if (binding.autoFrom.text.isNullOrEmpty()){
            Toast.makeText(
                requireContext(),
                "your Departure Airport still empty",
                Toast.LENGTH_SHORT
            ).show()
            a  = false
        }
        if (binding.autoTo.text.isNullOrEmpty()){
            Toast.makeText(requireContext(), "Your Arrival Airport still empty", Toast.LENGTH_SHORT)
                .show()
            a = false
        }
        if (binding.etDate.text.isNullOrEmpty()){
            Toast.makeText(requireContext(), "Your Departure Date still empty", Toast.LENGTH_SHORT)
                .show()
            a = false
        }
        if (binding.autoText.text.isNullOrEmpty()){
            Toast.makeText(requireContext(), "Your Seat Class still empty", Toast.LENGTH_SHORT)
                .show()
            a = false
        }
        return a
    }
    fun historyNotif(badge: BadgeDrawable, angka:Int?):Boolean{
        var a = false
        if(angka!=null && angka >0) {
            val x = angka
            badge.backgroundColor = ContextCompat.getColor(requireContext(), R.color.grey_base)
            badge.badgeTextColor = ContextCompat.getColor(requireContext(), R.color.white)
            badge.maxCharacterCount = 3
//        mainViewModel.getRead().observe(viewLifecycleOwner){
//            x -= it
//        }
            badge.number = x
            badge.isVisible = true
            a = true
        }
        return a
    }

}
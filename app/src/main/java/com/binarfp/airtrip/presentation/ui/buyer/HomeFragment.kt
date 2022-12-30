package com.binarfp.airtrip.presentation.ui.buyer

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.binarfp.airtrip.presentation.ui.auth.MainActivity
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
//        mainViewModel.getAccesToken().observe(viewLifecycleOwner){
//            mainViewModel.getHistory("Bearer $it")
//        }
//        mainViewModel.history.observe(viewLifecycleOwner){
//            val x = it.size
//            if(x>0){
//                historyNotif(x)
//            }
//            Log.e("his",it.toString()+x)
//        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.profile->{
                    findNavController().navigate(R.id.action_homeFragment_to_profileActivity)
                    true
                }
                R.id.notif->{
                    val intent = Intent(context,MainActivity::class.java)
                    mainViewModel.setAccessToken("")
                    startActivity(intent)
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
//        mainViewModel.airport1.observe(viewLifecycleOwner){
//            binding.etFrom.text = it.name
//            Log.e("asd",it.toString())
//            airport1 = it
//        }

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
            if (cekForm()){
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
    fun historyNotif(angka:Int){
        var x = angka
        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.history)
        badge.backgroundColor = ContextCompat.getColor(requireContext(),R.color.grey_base)
        badge.badgeTextColor = ContextCompat.getColor(requireContext(),R.color.white)
        badge.maxCharacterCount = 2
//        mainViewModel.getRead().observe(viewLifecycleOwner){
//            x -= it
//        }
        badge.number = x
        badge.isVisible = true

    }
}

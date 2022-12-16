package com.binarfp.airtrip.presentation.ui.buyer

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.FragmentHomeBinding
import com.binarfp.airtrip.model.DataAirport
import com.binarfp.airtrip.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val mainViewModel:MainViewModel by viewModels()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.profile->{
                    findNavController().navigate(R.id.action_homeFragment_to_profileActivity)
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
        mainViewModel.getAirports()
        var airports : List<DataAirport>
        airports = listOf(DataAirport(1,"asfk","alsf","lajndf"))
        var arrayAdapter : ArrayAdapter<DataAirport>
        arrayAdapter = ArrayAdapter(requireContext(),R.layout.drop_down_item,airports)
        mainViewModel.getAirport.observe(viewLifecycleOwner){
            airports = it
            arrayAdapter = ArrayAdapter(requireContext(),R.layout.drop_down_item,airports)
            binding.autoText1.setAdapter(arrayAdapter)
            binding.autoText2.setAdapter(arrayAdapter)
        }
        if (airports.size>1){
            var a = DataAirport(0,"null","null","null")
            var b = DataAirport(0,"null","null","null")
            binding.autoText1.setOnItemClickListener { adapterView, view, i, l ->
                a = arrayAdapter.getItem(i)!!
            }
            binding.autoText2.setOnItemClickListener { adapterView, view, i, l ->
                b = arrayAdapter.getItem(i)!!
            }
            binding.autoText1.setText(a.name)
        }
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val dateSetListener = DatePickerDialog(requireContext(),{ view, year, monthOfYear, dayOfMonth ->

            // Display Selected date in textbox
            val x = "$year"+"-"+ monthOfYear + "-" + dayOfMonth
            binding.etDate.setText(x)

        }, year, month, day)
        binding.etDate.setOnClickListener {
            dateSetListener.show()
        }
    }

    fun updateDateInView(){
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat,Locale.US)
        binding.etDate.setText(sdf.toString())
    }
}

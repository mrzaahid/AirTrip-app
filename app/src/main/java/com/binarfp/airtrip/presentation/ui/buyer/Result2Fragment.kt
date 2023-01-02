package com.binarfp.airtrip.presentation.ui.buyer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.FragmentResult2Binding
import com.binarfp.airtrip.model.DataAirport
import com.binarfp.airtrip.model.Flight
import com.binarfp.airtrip.presentation.MainViewModel
import com.binarfp.airtrip.presentation.ui.auth.MainActivity
import com.zaahid.challenge6.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Result2Fragment : Fragment() {
    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var binding: FragmentResult2Binding
    private val adapter:FlightAdapter by lazy {
        FlightAdapter{flight2->
            val bundle = Bundle()
            if (arguments?.getString("asal")=="round"){
                mainViewModel.getAccesToken().observe(viewLifecycleOwner){
                    if (it.isNullOrEmpty()){
                        val intent = Intent(context, MainActivity::class.java)
                        bundle.putString("asal","result")
                        startActivity(intent)
                    }else{
                        val flight = requireArguments().getSerializable("flight") as Flight
                        arguments?.getInt("id")?.let { it1 -> bundle.putInt("id", it1) }
                        bundle.putString("asal","round")
                        bundle.putSerializable("flight",flight)
                        bundle.putSerializable("flight2",flight2)
                        findNavController().navigate(R.id.action_result2Fragment_to_detailFragment,bundle)
                    }
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResult2Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList(binding.rvSearchResult)
        val airport1 = arguments?.getSerializable("airport1") as DataAirport
        val airport2 = arguments?.getSerializable("airport2") as DataAirport
        val date = arguments?.getString("date")
        val kelas = arguments?.getString("kelas")
        Log.e("lapor",airport1.toString()+airport2.toString()+date+kelas)
        mainViewModel.getFlights()
        mainViewModel.getFlights.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success->{
                    val result = it.payload?.data?.filter {data->
                        ((data.fromAirport?.id==airport2.id)&&(data.toAirport?.id==airport1.id))||
                                date?.let {it-> data.departure?.lowercase()?.indexOf(it) }!! > -1
                    }
                    if (result != null) {
                        adapter.submitData(result)
                    }
                    binding.rvSearchResult.visibility=View.VISIBLE
                    binding.pbAirport.visibility=View.GONE
                    binding.tvError.visibility = View.GONE
                }
                is Resource.Loading->{
                    binding.rvSearchResult.visibility=View.GONE
                    binding.pbAirport.visibility=View.VISIBLE
                    binding.tvError.visibility = View.GONE
                }
                is Resource.Error->{
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = it.exception.toString()
                    binding.rvSearchResult.visibility=View.GONE
                    binding.pbAirport.visibility=View.GONE
                }
                is Resource.Empty ->{}
            }
        }
    }
    private fun initList(view: View) {
        binding.rvSearchResult.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL,false)
            adapter = this@Result2Fragment.adapter
        }
    }
}
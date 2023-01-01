package com.binarfp.airtrip.presentation.ui.buyer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.FragmentResultBinding
import com.binarfp.airtrip.model.DataAirport
import com.binarfp.airtrip.presentation.MainViewModel
import com.binarfp.airtrip.presentation.ui.auth.MainActivity
import com.zaahid.challenge6.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : Fragment() {
    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var binding: FragmentResultBinding
    private val adapter:FlightAdapter by lazy {
        FlightAdapter{flight->
            val bundle = Bundle()
            if (arguments?.getString("asal")=="home"){
                mainViewModel.getAccesToken().observe(viewLifecycleOwner){
                    if (it.isNullOrEmpty()){
                        val intent = Intent(context,MainActivity::class.java)
                        bundle.putString("asal","result")
                        startActivity(intent)
                    }else{
                        arguments?.getInt("id")?.let { it1 -> bundle.putInt("id", it1) }
                        bundle.putString("asal","home")
                        bundle.putSerializable("flight",flight)
                        findNavController().navigate(R.id.action_resultFragment_to_detailFragment,bundle)
                    }
                }
            }
            if (arguments?.getString("asal")=="round"){
                mainViewModel.getAccesToken().observe(viewLifecycleOwner){
                    if (it.isNullOrEmpty()){
                        val intent = Intent(context,MainActivity::class.java)
                        bundle.putString("asal","result")
                        startActivity(intent)
                    }else{
                        val airport1 = arguments?.getSerializable("airport1") as DataAirport
                        val airport2 = arguments?.getSerializable("airport2") as DataAirport
                        val date = arguments?.getString("date")
                        val date2 = arguments?.getString("date2")
                        val kelas = arguments?.getString("kelas")
                        arguments?.getInt("id")?.let { it1 -> bundle.putInt("id", it1) }
                        bundle.putString("asal","round")
                        bundle.putSerializable("flight",flight)
                        bundle.putSerializable("airport1",airport1)
                        bundle.putSerializable("airport2",airport2)
                        bundle.putString("date",date)
                        bundle.putString("date2",date2)
                        bundle.putString("kelas",kelas)
                        findNavController().navigate(R.id.action_resultFragment_to_result2Fragment,bundle)
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
        binding = FragmentResultBinding.inflate(layoutInflater)
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
                        ((data.fromAirport?.id==airport1.id)&&(data.toAirport?.id==airport2.id))||
                                date?.let {it-> data.departure?.lowercase()?.indexOf(it) }!! > -1
                                kelas.let {it-> data.flightClass?.lowercase()?.indexOf(it.toString()) }!! > -1
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
            adapter = this@ResultFragment.adapter
        }
    }

}
package com.binarfp.airtrip.presentation.ui.buyer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.FragmentHistoryBinding
import com.binarfp.airtrip.presentation.MainViewModel
import com.zaahid.challenge6.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private lateinit var binding:FragmentHistoryBinding
    private val mainViewModel :MainViewModel by viewModels()
    private val adapter : HistoryAdapter by lazy {
        HistoryAdapter({
            val bundle = Bundle()
            bundle.putSerializable("boardingpass",it.boardingPasses?.boardingPassPergi)
            bundle.putString("name",it.passenger?.name)
            bundle.putString("asal","history")
            findNavController().navigate(R.id.action_historyFragment_to_boardingFragment,bundle)
        },{
            val bundle = Bundle()
            bundle.putSerializable("boardingpass",it.boardingPasses?.boardingPassPulang)
            bundle.putString("name",it.passenger?.name)
            bundle.putString("asal","history")
            findNavController().navigate(R.id.action_historyFragment_to_boardingFragment,bundle)
        })
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHistoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList(binding.rvAirport)
        mainViewModel.getAccesToken().observe(viewLifecycleOwner){
            mainViewModel.getHistory(it)
        }
        mainViewModel.history.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success->{
                    val result = it.payload?.data
                    if (result != null) {
                        adapter.submitData(result)
                    }
                    binding.rvAirport.visibility=View.VISIBLE
                    binding.pbAirport.visibility=View.GONE
                    binding.tvError.visibility = View.GONE
                }
                is Resource.Loading->{
                    binding.rvAirport.visibility=View.GONE
                    binding.pbAirport.visibility=View.VISIBLE
                    binding.tvError.visibility = View.GONE
                }
                is Resource.Error->{
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = it.exception.toString()
                    binding.rvAirport.visibility=View.GONE
                    binding.pbAirport.visibility=View.GONE
                }
                is Resource.Empty ->{
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = "EmptyList"
                    binding.rvAirport.visibility=View.GONE
                    binding.pbAirport.visibility=View.GONE
                }
            }
        }

    }
    private fun initList(view: View) {
        binding.rvAirport.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL,false)
            adapter = this@HistoryFragment.adapter
        }
    }
}
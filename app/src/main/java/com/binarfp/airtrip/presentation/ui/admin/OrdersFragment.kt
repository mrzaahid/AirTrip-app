package com.binarfp.airtrip.presentation.ui.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.FragmentOrdersBinding
import com.binarfp.airtrip.presentation.MainViewModel
import com.binarfp.airtrip.presentation.ui.buyer.HistoryAdapter
import com.zaahid.challenge6.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : Fragment() {
    private lateinit var binding:FragmentOrdersBinding
    private val mainViewModel : MainViewModel by viewModels()
    private val adapter : HistoryAdapter by lazy {
        HistoryAdapter({},{})
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrdersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList(binding.rvOrders)
        mainViewModel.getAccesToken().observe(viewLifecycleOwner){
            mainViewModel.getTickets(it)
        }
        mainViewModel.history.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success->{
                    val result = it.payload?.data
                    if (result != null) {
                        adapter.submitData(result)
                    }
                    binding.rvOrders.visibility=View.VISIBLE
                    binding.pbOrders.visibility=View.GONE
                    binding.tvErrorOrders.visibility = View.GONE
                }
                is Resource.Loading->{
                    binding.rvOrders.visibility=View.GONE
                    binding.pbOrders.visibility=View.VISIBLE
                    binding.tvErrorOrders.visibility = View.GONE
                }
                is Resource.Error->{
                    binding.tvErrorOrders.visibility = View.VISIBLE
                    binding.tvErrorOrders.text = it.exception.toString()
                    binding.rvOrders.visibility=View.GONE
                    binding.pbOrders.visibility=View.GONE
                }
                is Resource.Empty ->{
                    binding.tvErrorOrders.visibility = View.VISIBLE
                    binding.tvErrorOrders.text = "EmptyList"
                    binding.rvOrders.visibility=View.GONE
                    binding.pbOrders.visibility=View.GONE
                }
            }
        }
    }

    private fun initList(view: View) {
        binding.rvOrders.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL,false)
            adapter = this@OrdersFragment.adapter
        }
    }
}
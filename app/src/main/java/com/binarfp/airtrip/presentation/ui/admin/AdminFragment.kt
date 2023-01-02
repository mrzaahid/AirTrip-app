package com.binarfp.airtrip.presentation.ui.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.FragmentAdminBinding
import com.binarfp.airtrip.presentation.MainViewModel
import com.zaahid.challenge6.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminFragment : Fragment() {
    private lateinit var binding:FragmentAdminBinding
    private val mainViewModel:MainViewModel by  viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =FragmentAdminBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cardOrders.setOnClickListener {
            findNavController().navigate(R.id.action_adminFragment_to_ordersFragment)
        }
        binding.cardFlight.setOnClickListener {
            findNavController().navigate(R.id.action_adminFragment_to_flightFragment) }
        mainViewModel.getAccesToken().observe(viewLifecycleOwner){
            mainViewModel.getProfile(it)
        }
        mainViewModel.responseUser.observe(viewLifecycleOwner){
            if (it is Resource.Success){
                binding.textView12.text = "Admin ${it.payload?.data?.name}"
            }
        }
    }
}
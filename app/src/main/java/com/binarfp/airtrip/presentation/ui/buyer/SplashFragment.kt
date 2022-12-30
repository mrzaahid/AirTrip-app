package com.binarfp.airtrip.presentation.ui.buyer

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.FragmentSplashBinding
import com.binarfp.airtrip.model.DataAirport
import com.binarfp.airtrip.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    private val mainViewModel:MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_splashFragment_to_homeActivity)
        }
        binding.button2.setOnClickListener {
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }
        mainViewModel.getAccesToken().observe(viewLifecycleOwner){
            if (cekStatus(it)){
                findNavController().navigate(R.id.action_splashFragment_to_homeActivity)
            }
        }
    }
    fun cekStatus(accesstoken:String):Boolean{
        var x = true
        if (accesstoken.isEmpty()){
            x=false
        }
        return x
    }
}
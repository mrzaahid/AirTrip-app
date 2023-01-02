package com.binarfp.airtrip.presentation.ui.buyer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.FragmentAirplaneBinding
import com.binarfp.airtrip.model.Airplane
import com.binarfp.airtrip.presentation.MainViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AirplaneFragment : Fragment() {

    private lateinit var binding:FragmentAirplaneBinding
    private val mainViewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAirplaneBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val airplane = requireArguments().getSerializable("airplane") as Airplane
        Glide.with(this)
            .load(airplane.image)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_airplane_240)
                    .error(R.drawable.ic_airplane_240))
            .into(binding.imageAirplane)
        binding.tvManufacture.text = airplane.manufacture
        binding.tvModelNumber.text = airplane.modelNumber
        binding.tvCapacity.text = airplane.capacity.toString()
        binding.tvSpecs.text =  airplane.specs?.toString()




    }
}
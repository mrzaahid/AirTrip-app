package com.binarfp.airtrip.presentation.ui.admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.FragmentAddAirplaneBinding
import com.binarfp.airtrip.presentation.MainViewModel
import com.zaahid.challenge6.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAirplaneFragment : Fragment() {
    private lateinit var binding : FragmentAddAirplaneBinding
    private val mainViewModel :MainViewModel by viewModels()
    private val adapter :AirplaneAdapter by  lazy {
        AirplaneAdapter(
            {
            val bundle = Bundle()
                bundle.putString("asal","update")
                bundle.putSerializable("airplane",it)
            },
            {},
            "addairplane"
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddAirplaneBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList(view)
        mainViewModel.getAccesToken().observe(viewLifecycleOwner){
            mainViewModel.getAirplane(it)
        }
        val searchview = binding.searchFlightAdmin
        searchview.queryHint = getString(R.string.search)
        searchview.isIconified = false
        mainViewModel.responsesAirplane.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    val result = it.payload?.data
                    if (result != null) {
                        adapter.submitData(result)
                    }
                    binding.rvFlightAdmin.visibility = View.VISIBLE
                    binding.pbFlightAdmin.visibility = View.GONE
                    binding.tvErrorFlightAdmin.visibility = View.GONE
                }
                is Resource.Loading -> {
                    binding.rvFlightAdmin.visibility = View.GONE
                    binding.pbFlightAdmin.visibility = View.VISIBLE
                    binding.tvErrorFlightAdmin.visibility = View.GONE
                }
                is Resource.Error -> {
                    binding.tvErrorFlightAdmin.visibility = View.VISIBLE
                    binding.tvErrorFlightAdmin.text = it.exception.toString()
                    binding.rvFlightAdmin.visibility = View.GONE
                    binding.pbFlightAdmin.visibility = View.GONE
                }
                is Resource.Empty -> {

                }
            }
        }
        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { a ->
                    mainViewModel.responsesAirplane.observe(viewLifecycleOwner) {
                        when (it) {
                            is Resource.Success -> {
                                var search = a
                                if (a.isDigitsOnly()){
                                    val result = it.payload?.data?.filter { data ->
                                        data.id.toString().indexOf(a)>-1
                                    }
                                    if (result != null) {
                                        adapter.submitData(result)
                                    }
                                }else{
                                    val result = it.payload?.data?.filter { data ->
                                        ((data.manufacture?.lowercase()?.indexOf(a)
                                            ?: -1) > -1) ||
                                                ((data.modelNumber?.lowercase()?.indexOf(a)
                                                    ?: -1) > -1)
                                    }
                                    if (result != null) {
                                        adapter.submitData(result)
                                    }
                                }
                                binding.rvFlightAdmin.visibility = View.VISIBLE
                                binding.pbFlightAdmin.visibility = View.GONE
                                binding.tvErrorFlightAdmin.visibility = View.GONE
                            }
                            is Resource.Loading -> {
                                binding.rvFlightAdmin.visibility = View.GONE
                                binding.pbFlightAdmin.visibility = View.VISIBLE
                                binding.tvErrorFlightAdmin.visibility = View.GONE
                            }
                            is Resource.Error -> {
                                binding.tvErrorFlightAdmin.visibility = View.VISIBLE
                                binding.tvErrorFlightAdmin.text = it.exception.toString()
                                binding.rvFlightAdmin.visibility = View.GONE
                                binding.pbFlightAdmin.visibility = View.GONE
                            }
                            is Resource.Empty -> {

                            }
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    Log.e("search",newText)}
                return false
            }
        })
        binding.button5.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("asal","add")
            findNavController().navigate(R.id.action_addAirplaneFragment_to_formAirplaneFragment,bundle)
        }

    }
    private fun initList(view: View) {
        binding.rvFlightAdmin.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            adapter = this@AddAirplaneFragment.adapter
        }
    }

}
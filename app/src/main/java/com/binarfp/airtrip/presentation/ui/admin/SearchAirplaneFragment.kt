package com.binarfp.airtrip.presentation.ui.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.FragmentSearchAirplaneBinding
import com.binarfp.airtrip.model.Airplane
import com.binarfp.airtrip.presentation.MainViewModel
import com.zaahid.challenge6.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchAirplaneFragment : Fragment() {
    private lateinit var binding: FragmentSearchAirplaneBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val adapter: AirplaneAdapter by lazy {
        AirplaneAdapter(
            {
                val bundle = Bundle()
                if (requireArguments().getString("asal") == "formflight") {
                    bundle.putString("asal","formflight")
                    bundle.putSerializable("airplane", it)
                    findNavController().navigate(R.id.action_searchAirplaneFragment_to_flightFormFragment)
                }
            }, {}
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
        binding = FragmentSearchAirplaneBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList(view)
        mainViewModel.getAccesToken().observe(viewLifecycleOwner) {
            mainViewModel.getAirplane(it)
        }
        mainViewModel.responsesAirplane.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.payload?.let { it1 -> adapter.submitData(it1.data) }

                    binding.rvAirplane.visibility = View.VISIBLE
                    binding.pbAirplane.visibility = View.GONE
                    binding.tvErrorAirplane.visibility = View.GONE
                }
                is Resource.Loading -> {
                    binding.rvAirplane.visibility = View.GONE
                    binding.pbAirplane.visibility = View.VISIBLE
                    binding.tvErrorAirplane.visibility = View.GONE
                }
                is Resource.Error -> {
                    binding.tvErrorAirplane.visibility = View.VISIBLE
                    binding.tvErrorAirplane.text = it.exception.toString()
                    binding.rvAirplane.visibility = View.GONE
                    binding.pbAirplane.visibility = View.GONE
                }
                is Resource.Empty -> {

                }
            }
        }
        val searchview = binding.searchMovie
        searchview.queryHint = getString(R.string.search)
        searchview.isIconified = false
        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { a ->
                    mainViewModel.responsesAirplane.observe(viewLifecycleOwner) {
                        when (it) {
                            is Resource.Success -> {
                                var result: List<Airplane>? = null
                                if (a.isDigitsOnly()) {
                                    result = it.payload?.data?.filter { data ->
                                        data.capacity!! > a.toInt()
                                    }
                                    if (result != null) {
                                        adapter.submitData(result)
                                    }
                                } else {
                                    result = it.payload?.data?.filter { data ->
                                        data.manufacture?.lowercase()!!.indexOf(a) > -1 ||
                                                data.modelNumber?.lowercase()!!.indexOf(a) > -1 ||
                                                (data.manufacture + " " + data.modelNumber).lowercase()
                                                    .indexOf(a) > -1
                                    }
                                }
                                if (result != null) {
                                    adapter.submitData(result)
                                }
                                binding.rvAirplane.visibility = View.VISIBLE
                                binding.pbAirplane.visibility = View.GONE
                                binding.tvErrorAirplane.visibility = View.GONE
                            }
                            is Resource.Loading -> {
                                binding.rvAirplane.visibility = View.GONE
                                binding.pbAirplane.visibility = View.VISIBLE
                                binding.tvErrorAirplane.visibility = View.GONE
                            }
                            is Resource.Error -> {
                                binding.tvErrorAirplane.visibility = View.VISIBLE
                                binding.tvErrorAirplane.text = it.exception.toString()
                                binding.rvAirplane.visibility = View.GONE
                                binding.pbAirplane.visibility = View.GONE
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
                    Log.e("search", newText)
                }
                return false
            }
        }
        )
    }
    private fun initList(view: View) {
        binding.rvAirplane.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            adapter = this@SearchAirplaneFragment.adapter
        }
    }

}
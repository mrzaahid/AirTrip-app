package com.binarfp.airtrip.presentation.ui.buyer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.FragmentSearchAirportBinding
import com.binarfp.airtrip.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchAirportFragment : Fragment() {
    private lateinit var binding:FragmentSearchAirportBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val adapter:AirportAdapter by lazy {
        AirportAdapter{
            val bundle = Bundle()
            if (arguments?.getInt("id")==1){
                arguments?.getInt("id")?.let { it1 -> bundle.putInt("id", it1) }
                bundle.putSerializable("airport1",it)
                findNavController().navigate(R.id.action_searchAirportFragment_to_searchAirport2,bundle)
            }
            if (arguments?.getInt("id")==2){
                arguments?.getInt("id")?.let { it1 -> bundle.putInt("id", it1) }
                bundle.putSerializable("airport1",it)
                findNavController().navigate(R.id.action_searchAirportFragment_to_searchAirport2,bundle)
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
        binding = FragmentSearchAirportBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.titleSearch.text = "Search Departure Airport"
        mainViewModel.getAirports()
        val searchview = binding.searchMovie
        searchview.queryHint = getString(R.string.search)
        searchview.isIconified = false
        mainViewModel.getAirport.observe(viewLifecycleOwner){
            adapter.submitData(it)
        }
        initList(view)
        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {a->
                    mainViewModel.getAirport.observe(viewLifecycleOwner){list->
                        val result = list.filter {data->
                            (data.name.lowercase().indexOf(a) >-1) ||
                                    (data.iata.lowercase().indexOf(a)>-1)||
                                    (data.address.lowercase().indexOf(a)>-1)
                        }
                        adapter.submitData(result)
//                        Log.e("search",result.toString())
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                if (newText != null) {
//                    mainViewModel.searchListWName(newText,viewLifecycleOwner)
//                }
//                mainViewModel.listAirport.observe(viewLifecycleOwner, Observer {
//                    adapter.submitData(it)
//                    Log.e("list",it.toString())
//                })
                if (newText != null) {
                    Log.e("search",newText)}
                return false
            }
        })
    }
    private fun initList(view: View) {
        binding.rvAirport.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL,false)
            adapter = this@SearchAirportFragment.adapter
        }
    }
}
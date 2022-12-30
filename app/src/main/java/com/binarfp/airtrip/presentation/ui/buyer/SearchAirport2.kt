package com.binarfp.airtrip.presentation.ui.buyer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.FragmentSearchAirportBinding
import com.binarfp.airtrip.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchAirport2 : Fragment() {
    private lateinit var binding: FragmentSearchAirportBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val adapter:AirportAdapter by lazy {
        AirportAdapter{
            val bundle = Bundle()
            bundle.putSerializable("airport2",it)
            arguments?.getSerializable("airport1").let { it1-> bundle.putSerializable("airport1",it1) }
//            Log.e("airport2",it.toString())
//            Log.e("id",arguments?.getInt("id").toString())
            if (arguments?.getInt("id")==1){
                bundle.putInt("id",1)
                findNavController().navigate(R.id.action_searchAirport2_to_homeFragment,bundle)
            }
            if (arguments?.getInt("id")==2){
                bundle.putInt("id",2)
                findNavController().navigate(R.id.action_searchAirport2_to_roundFragment,bundle)
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
        binding.titleSearch.text = "Search Arrival Airport"
        mainViewModel.getAirports()
        Log.e("aiport1", arguments?.getSerializable("airport1").toString())
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
            adapter = this@SearchAirport2.adapter
        }
    }
}
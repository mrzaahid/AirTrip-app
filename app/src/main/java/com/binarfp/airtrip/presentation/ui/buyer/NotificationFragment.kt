package com.binarfp.airtrip.presentation.ui.buyer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.FragmentNotificationBinding
import com.binarfp.airtrip.presentation.MainViewModel
import com.binarfp.airtrip.presentation.ui.auth.MainActivity
import com.zaahid.challenge6.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private val mainViewModel : MainViewModel by viewModels()
    private val adapter : NotificationAdapter by lazy {
        NotificationAdapter{notif->
            mainViewModel.getAccesToken().observe(viewLifecycleOwner){token->
                if (token.isNullOrEmpty()){
                    val intent = Intent(context,MainActivity::class.java)
                    startActivity(intent)
                }else{
                    mainViewModel.getHistory(token)
                    mainViewModel.history.observe(viewLifecycleOwner){
                        val data = it.payload?.data
                        data?.let {
                            val ticket = it.first {
                                it.id == notif.ticketId
                            }
                            ticket
                            val bundle = Bundle()
                            bundle.putSerializable("ticket",ticket)
                            findNavController().navigate(R.id.action_notificationFragment_to_notifFragment,bundle)
                        }
                    }
                }
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList(binding.rv)
        mainViewModel.getAccesToken().observe(viewLifecycleOwner){
            mainViewModel.getHistory(it)
            mainViewModel.getNotif(it)
        }
        mainViewModel.responsesNotif.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success->{
                    val result = it.payload?.data
                    if (result != null) {
                        adapter.submitData(result)
                    }
                    binding.rv.visibility=View.VISIBLE
                    binding.pb.visibility=View.GONE
                    binding.tvError.visibility = View.GONE
                }
                is Resource.Loading->{
                    binding.rv.visibility=View.GONE
                    binding.pb.visibility=View.VISIBLE
                    binding.tvError.visibility = View.GONE
                }
                is Resource.Error->{
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = it.exception.toString()
                    binding.rv.visibility=View.GONE
                    binding.pb.visibility=View.GONE
                }
                is Resource.Empty ->{
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = "EmptyList"
                    binding.rv.visibility=View.GONE
                    binding.pb.visibility=View.GONE
                }
            }
        }
    }
    private fun initList(view: View) {
        binding.rv.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL,false)
            adapter = this@NotificationFragment.adapter
        }
    }
}
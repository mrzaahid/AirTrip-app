package com.binarfp.airtrip.presentation.ui.buyer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.FragmentNotifBinding
import com.binarfp.airtrip.model.HistoryData
import com.binarfp.airtrip.model.Ticket
import com.binarfp.airtrip.presentation.MainViewModel


class NotifFragment : Fragment() {
    private lateinit var binding: FragmentNotifBinding
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotifBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ticket = arguments?.getSerializable("ticket") as HistoryData
        val invoice = ticket.invoiceNumber
        binding.tvPassengerName.text = ticket.passenger?.name
        binding.tvFlightType.text = ticket.flightType
        binding.tvTotalPrice.text = ticket.totalPrice.toString()
        binding.tvInvoice.text = invoice
        val encoder = QRGEncoder(invoice, null, QRGContents.Type.TEXT, 600)
        binding.imageView5.setImageBitmap(encoder.bitmap)
        binding.button3.setOnClickListener { it1 ->
            val bundle = Bundle()
            bundle.putSerializable("boardingpass", ticket.boardingPasses?.boardingPassPergi)
            bundle.putString("name", ticket.passenger?.name)
            bundle.putString("asal","history")
            findNavController().navigate(R.id.action_notifFragment_to_boardingFragment, bundle)
        }
        if (ticket.flightType?.lowercase()=="roundtrip"){
            binding.btnPulang.visibility = View.VISIBLE
            binding.btnPulang.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable("boardingpass", ticket.boardingPasses?.boardingPassPulang)
                bundle.putString("name", ticket.passenger?.name)
                bundle.putString("asal","history")
                findNavController().navigate(R.id.action_notifFragment_to_boardingFragment, bundle)
            }
        }
    }
}
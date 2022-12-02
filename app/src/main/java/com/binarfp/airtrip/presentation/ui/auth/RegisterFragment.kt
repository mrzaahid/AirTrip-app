package com.binarfp.airtrip.presentation.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.FragmentRegisterBinding
import com.binarfp.airtrip.presentation.MainViewModel
import com.binarfp.airtrip.presentation.Utils
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var binding: FragmentRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignup.setOnClickListener {
            if (checkForm()){ tryregister() }
        }
    }

    fun checkForm():Boolean{
        var a = true
        if (Utils.isempty(binding.etUsername)){
            Toast.makeText(context, "username is empty", Toast.LENGTH_SHORT).show()
            a = false
        }
        if (binding.etNumber.text.isEmpty()){
            a = false
            Toast.makeText(context, "phone number is empty", Toast.LENGTH_SHORT).show()
        }
        if (Utils.isempty(binding.etEmail)){
            a = false
            Toast.makeText(context, "email is empty", Toast.LENGTH_SHORT).show()
        }
        if(Utils.isempty(binding.etAddress)){
            a = false
            Toast.makeText(context, "address is empty", Toast.LENGTH_SHORT).show()
        }
        if (Utils.isempty(binding.etPassword)){
            a = false
            Toast.makeText(context, "password is empty", Toast.LENGTH_SHORT).show()
        }
        return a
    }
    fun tryregister(){
        try {
            val jsonObject = JSONObject()
            jsonObject.put("email", binding.etEmail.text.toString())
            jsonObject.put("password", binding.etPassword.text.toString())
            jsonObject.put("phone", binding.etNumber.text.toString())
            jsonObject.put("name", binding.etUsername.text.toString() )
            jsonObject.put("address", binding.etAddress.text.toString())
            val jsonObjectString =jsonObject.toString()
            val requestBody =jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            mainViewModel.registrasi(requestBody)
            mainViewModel.registResult.observe(viewLifecycleOwner){
                if (it.isSuccessful){
                    Toast.makeText(context, "signup succeed", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }else{
                    if (it.code()==422){
                        Toast.makeText(
                            context,
                            "This email already registered",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }catch (e:Error){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
            Log.e("error",e.toString())
        }
    }

}
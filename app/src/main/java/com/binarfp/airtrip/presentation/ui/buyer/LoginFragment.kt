@file:OptIn(DelicateCoroutinesApi::class)

package com.binarfp.airtrip.presentation.ui.buyer

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
import com.binarfp.airtrip.databinding.FragmentLoginBinding
import com.binarfp.airtrip.presentation.MainViewModel
import com.binarfp.airtrip.presentation.Utils.isempty
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            if (checkForm()){
                tryLogin()
            }
        }
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    fun checkForm():Boolean{
        var a = true
        if (isempty(binding.etLoginEmail)){
            Toast.makeText(context, "email is empty", Toast.LENGTH_SHORT).show()
            a = false
        }
        if (isempty(binding.etPassword)){
            Toast.makeText(context, "password is empty", Toast.LENGTH_SHORT).show()
            a = false
        }
        return a
    }
    fun tryLogin(){
        try {
            val jsonObject = JSONObject()
            jsonObject.put("email", binding.etLoginEmail.text.toString())
            jsonObject.put("password", binding.etPassword.text.toString())
            val jsonObjectString =jsonObject.toString()
            val requestBody =jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            mainViewModel.login(requestBody)
            mainViewModel.loginResult.observe(viewLifecycleOwner){
//                GlobalScope.launch {
//                    delay(2000)
//                }
                if (it.isSuccessful){
                    Toast.makeText(context, "sign in succeed ${it.body()!!.accessToken}", Toast.LENGTH_SHORT).show()
                }else{
                    if(it.code()==401){
                        Toast.makeText(
                            context,
                            "That is not the password? " +
                                    "who are you?",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    if (it.code()==404){
                        Toast.makeText(
                            context,
                            "there not email like that in our database ?",
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
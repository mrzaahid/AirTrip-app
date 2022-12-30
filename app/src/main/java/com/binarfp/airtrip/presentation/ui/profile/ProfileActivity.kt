package com.binarfp.airtrip.presentation.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.binarfp.airtrip.databinding.ActivityProfileBinding
import com.binarfp.airtrip.presentation.MainViewModel
import com.binarfp.airtrip.presentation.ui.auth.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val mainViewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogout.setOnClickListener {
            mainViewModel.setAccessToken("")
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUserInfo(){
    }


}
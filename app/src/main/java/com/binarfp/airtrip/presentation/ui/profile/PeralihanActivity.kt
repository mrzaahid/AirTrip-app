package com.binarfp.airtrip.presentation.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binarfp.airtrip.R

class PeralihanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_peralihan)
        val intent = Intent(this,ProfileActivity::class.java)
        startActivity(intent)
    }
}
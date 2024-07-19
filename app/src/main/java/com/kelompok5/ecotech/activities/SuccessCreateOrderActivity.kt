package com.kelompok5.ecotech.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.databinding.ActivitySuccessCreateOrderBinding

class SuccessCreateOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuccessCreateOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySuccessCreateOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnKembaliSuccessorder.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java ))
            finishAffinity()
        }
    }
}
package com.kelompok5.ecotech.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kelompok5.ecotech.databinding.ActivityEmailTerkirimLupaPasswordBinding

class EmailTerkirimLupaPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmailTerkirimLupaPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailTerkirimLupaPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnKembaliEmailterkirim.setOnClickListener {
            finish()
        }
    }
}
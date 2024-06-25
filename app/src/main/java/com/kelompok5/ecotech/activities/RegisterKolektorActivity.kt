package com.kelompok5.ecotech.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.databinding.ActivityRegisterKolektorBinding

class RegisterKolektorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterKolektorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterKolektorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
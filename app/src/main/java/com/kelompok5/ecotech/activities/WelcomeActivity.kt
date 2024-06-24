package com.kelompok5.ecotech.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kelompok5.ecotech.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cvPenyetor.setOnClickListener {
            val intent = Intent(this, RegisterPenyetorActivity::class.java)
            startActivity(intent)
        }
        binding.cvKolektor.setOnClickListener {
            val intent = Intent(this, RegisterPenyetorActivity::class.java)
            startActivity(intent)
        }
    }
}
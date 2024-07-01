package com.kelompok5.ecotech.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kelompok5.ecotech.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private var backPressedTime: Long = 0
    private lateinit var toast: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cvPenyetor.setOnClickListener {
            val intent = Intent(this, RegisterPenyetorActivity::class.java)
            startActivity(intent)
        }
        binding.cvKolektor.setOnClickListener {
            val intent = Intent(this, HomeKolektorActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            toast.cancel()
            super.onBackPressed()
            finishAffinity()
        } else {
            toast = Toast.makeText(this, "Ketuk sekali lagi untuk keluar dari aplikasi!", Toast.LENGTH_SHORT)
            toast.show()
        }
        backPressedTime = System.currentTimeMillis()
    }

}
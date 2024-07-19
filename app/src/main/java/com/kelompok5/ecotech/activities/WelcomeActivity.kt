package com.kelompok5.ecotech.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.kelompok5.ecotech.DataStoreManager
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.databinding.ActivityWelcomeBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private var backPressedTime: Long = 0
    private lateinit var toast: Toast
    private lateinit var dataStoreManager: DataStoreManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUi()
//        dataStoreManager = DataStoreManager.getInstance(this)
//        lifecycleScope.launch {
//            val isLoggedIn = dataStoreManager.isLoggedIn.first()
//            val roleId = dataStoreManager.roleId.first()
//            Log.d("WelcomeActivity", "isLoggedIn: $isLoggedIn, roleId: $roleId")
//            if (isLoggedIn && roleId != null) {
//                navigateToHomeScreen(roleId)
//                finish() // Finish this activity to prevent back navigation
//            } else {
//                setupUi()
//            }
//        }

    }

    private fun setupUi() {
        binding.cvPenyetor.setOnClickListener {
            val intent = Intent(this, RegisterPenyetorActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.cvKolektor.setOnClickListener {
            val intent = Intent(this, RegisterKolektorActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

//    private fun navigateToHomeScreen(roleid: Int?) {
//        val intent = when (roleid) {
//            2 -> Intent(this, MainActivity::class.java) // Assuming roleid 2 is for Kolektor
//            3 -> Intent(this, HomeKolektorActivity::class.java) // Assuming roleid 3 is for Penyetor
//            else -> null // Default activity
//        }
//        intent?.let {
//            startActivity(it)
//            finish()
//        } ?: run {
//            // Stay in WelcomeActivity if roleid is null or invalid
//            setupUi()
//        }
//    }

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
package com.kelompok5.ecotech.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kelompok5.ecotech.DataStoreManager
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.databinding.ActivityWelcomeBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 2500
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        dataStoreManager = DataStoreManager.getInstance(this)

        Handler().postDelayed({
            startActivity(Intent(this@SplashScreenActivity, WelcomeActivity::class.java))
            lifecycleScope.launch {
                val isLoggedIn = dataStoreManager.isLoggedIn.first()
                val roleId = dataStoreManager.roleId.first()
                Log.d("WelcomeActivity", "isLoggedIn: $isLoggedIn, roleId: $roleId")
                if (isLoggedIn && roleId != null) {
                    navigateToHomeScreen(roleId)
                    finish()
                }
                finish()
            }
        }, SPLASH_TIME_OUT)
    }

    private fun navigateToHomeScreen(roleid: Int?) {
        val intent = when (roleid) {
            2 -> Intent(this, MainActivity::class.java) // Role Penyetor
            3 -> Intent(this, HomeKolektorActivity::class.java) // Role Kolektor
            else -> Intent(this, WelcomeActivity::class.java) // Go to Welcome if have no saved role
        }
        startActivity(intent)
        finish()
    }

}
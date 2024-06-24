package com.kelompok5.ecotech.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kelompok5.ecotech.EcotechApp
import com.kelompok5.ecotech.data.model.request.LoginRequestBody
import com.kelompok5.ecotech.databinding.ActivityLoginBinding
import com.kelompok5.ecotech.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmailLogin.text.toString()
            val password = binding.edtPasswordLogin.text.toString()

            val loginRequestBody = LoginRequestBody(email, password)
            authViewModel.loginUser(loginRequestBody)
        }

        binding.textRegister.setOnClickListener {
            finish()
        }
        binding.tvLupaPassword.setOnClickListener {
            val intent = Intent(this, LupaPasswordActivity::class.java)
            startActivity(intent)
        }

        observeLogin()
    }

    private fun observeLogin() {
        authViewModel.isLoading.observe(this) { isLoading ->
            binding.btnLogin.isEnabled = !isLoading
        }

        authViewModel.loginUser.observe(this) { loginResponse ->
            if (loginResponse != null) {
                Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show()
                val fullEmail = binding.edtEmailLogin.text.toString()
                val pass = binding.edtPasswordLogin.text.toString()
                val emailBeforeAt = binding.edtEmailLogin.text.toString().split("@")[0]

                val sharedPref = EcotechApp.context.getSharedPreferences("ecoTechPref", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("emailShort", emailBeforeAt)
                    putString("email", fullEmail)
                    putString("password", pass)
                    apply()
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        authViewModel.errorMessage.observe(this) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
}
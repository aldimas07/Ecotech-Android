package com.irhamsoetomo.ecotech.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.irhamsoetomo.ecotech.EcotechApp
import com.irhamsoetomo.ecotech.data.model.request.LoginRequestBody
import com.irhamsoetomo.ecotech.databinding.ActivityLoginBinding
import com.irhamsoetomo.ecotech.viewmodel.AuthViewModel
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
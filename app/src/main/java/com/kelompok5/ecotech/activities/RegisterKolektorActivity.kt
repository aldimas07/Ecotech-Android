package com.kelompok5.ecotech.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kelompok5.ecotech.EcotechApp
import com.kelompok5.ecotech.data.model.request.LoginRequestBody
import com.kelompok5.ecotech.data.model.request.RegisterUserKolektorRequestBody
import com.kelompok5.ecotech.databinding.ActivityRegisterKolektorBinding
import com.kelompok5.ecotech.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterKolektorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterKolektorBinding
    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterKolektorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(checkSharedPrefForAutoLogin()) {
            startActivity(Intent(this, HomeKolektorActivity::class.java))
            finish()
        }
        observeRegister()

        binding.btnRegisterKolektor.setOnClickListener{
            val name = binding.userNamaKolektor.text.toString()
            val email = binding.edtEmailRegisterKolektor.text.toString()
            val alamat = binding.edtAlamatRegisterKolektor.text.toString()
            val nohp = binding.edtNohpRegisterKolektor.text.toString()
            val password = binding.edtPasswordRegisterKolektor.text.toString()
            val confirmPassword = binding.confirmPasswordKolektor.text.toString()

            if (password == confirmPassword) {
                val registerKolektorRequestBody = RegisterUserKolektorRequestBody(name,email,alamat,nohp,password,confirmPassword)
                authViewModel.registerUserKolektor(registerKolektorRequestBody)
            }
        }

        binding.textLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun observeRegister(){
        authViewModel.isLoading.observe(this) { isLoading ->
            binding.btnRegisterKolektor.isEnabled = !isLoading
        }

        authViewModel.registerUserKolektor.observe(this) { registerResponse ->
            if (registerResponse != null) {
                Toast.makeText(this, "Register berhasil", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Register gagal", Toast.LENGTH_SHORT).show()
            }
        }
        authViewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkSharedPrefForAutoLogin(): Boolean {
        val sharedPref = EcotechApp.context.getSharedPreferences("ecoTechPref", Context.MODE_PRIVATE)
        val email = sharedPref.getString("email", null)
        val password = sharedPref.getString("password", null)

        return if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            authViewModel.loginUser(LoginRequestBody(email, password)) //Assuming you have this method in your ViewModel
            true
        } else false
    }
}


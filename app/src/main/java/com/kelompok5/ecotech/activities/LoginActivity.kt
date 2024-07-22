package com.kelompok5.ecotech.activities

import  android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.kelompok5.ecotech.DataStoreManager
import com.kelompok5.ecotech.EcotechApp
import com.kelompok5.ecotech.data.model.request.LoginRequestBody
import com.kelompok5.ecotech.data.remote.RetrofitClient
import com.kelompok5.ecotech.databinding.ActivityLoginBinding
import com.kelompok5.ecotech.viewmodel.AuthViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val authViewModel: AuthViewModel by viewModel()
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStoreManager = DataStoreManager.getInstance(this)

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmailLogin.text.toString()
            val password = binding.edtPasswordLogin.text.toString()

            if (email.isEmpty()) {
                binding.edtEmailLogin.error = "Email tidak boleh kosong"
                return@setOnClickListener
            } else if (password.isEmpty()) {
                binding.edtPasswordLogin.error = "Password tidak boleh kosong"
                return@setOnClickListener
            } else {
                binding.edtEmailLogin.error = null
                binding.edtPasswordLogin.error = null
            }

            val loginRequestBody = LoginRequestBody(email, password)
            authViewModel.loginUser(loginRequestBody)
            observeLogin()
        }

        binding.textRegister.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
        }
        binding.tvLupaPassword.setOnClickListener {
            val intent = Intent(this, LupaPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeLogin() {
        authViewModel.isLoading.observe(this) { isLoading ->
            binding.btnLogin.isEnabled = !isLoading
        }

        authViewModel.loginUser.observe(this) { loginResponse ->
            Log.d("LoginResponse", loginResponse.toString())
            if (loginResponse != null) {
                lifecycleScope.launch {
                    dataStoreManager.saveId(loginResponse.data.id)
                    dataStoreManager.saveName(loginResponse.data.name)
                    dataStoreManager.saveToken(loginResponse.data.accessToken)
                    dataStoreManager.saveLoginStatus(true)
                    dataStoreManager.saveRoleId(loginResponse.data.roleid)
                }
                if (loginResponse.data.roleid == 3) {
                    Toast.makeText(this, "Login Kolektor success!", Toast.LENGTH_SHORT).show()
                    val Intent = Intent(this, HomeKolektorActivity::class.java)
                    intent.putExtra(HomeKolektorActivity.EXTRA_KOLEKTOR_NAME, loginResponse.data.name)
                    startActivity(Intent)
                } else if (loginResponse.data.roleid == 2){
                    Toast.makeText(this, "Login Penyetor success!", Toast.LENGTH_SHORT).show()
                    val Intent = Intent(this, MainActivity::class.java)
                    intent.putExtra(MainActivity.EXTRA_PENYETOR_NAME, loginResponse.data.name)
                    startActivity(Intent)
                } else {
                    Toast.makeText(this, "Login ditolak.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        authViewModel.errorMessage.observe(this) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, WelcomeActivity::class.java))
        finish()
        finishAffinity()
    }
}


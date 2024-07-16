package com.kelompok5.ecotech.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.annotation.Discouraged
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kelompok5.ecotech.DataStoreManager
import com.kelompok5.ecotech.EcotechApp
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.data.remote.ApiService
import com.kelompok5.ecotech.data.remote.RetrofitClient.retrofit
import com.kelompok5.ecotech.data.repository.EcotechRepository
import com.kelompok5.ecotech.databinding.ActivityAccountInfoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountInfoBinding
    private lateinit var repository: EcotechRepository
    private lateinit var dataStoreManager: DataStoreManager
    private val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        dataStoreManager = DataStoreManager.getInstance(this)
        repository = EcotechRepository(apiService)

        lifecycleScope.launch {
            val name = dataStoreManager.name.first().toString()
            binding.tvNama.text = name
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu1 -> {
                    showLogoutDialog()
                    true
                }
                else -> false
            }
        }
    }


    private fun logout(){
        lifecycleScope.launch {
            val token = dataStoreManager.token.first()
            if (token != null) {
                val response = repository.logoutUser(token)
                if (response.isSuccessful) {
                    // Clear DataStore
                    dataStoreManager.saveLoginStatus(false)
                    dataStoreManager.saveRoleId(0)
                    dataStoreManager.saveToken("")

                    Toast.makeText(this@AccountInfoActivity, "Logged out successfully", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@AccountInfoActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    Toast.makeText(this@AccountInfoActivity, "Logout failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@AccountInfoActivity, "Token not found", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@AccountInfoActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Apakah kamu mau logout?")
            .setCancelable(false)
            .setPositiveButton("Iya") { dialog, id ->
                logout()
            }
            .setNegativeButton("Nggak") { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
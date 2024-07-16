package com.kelompok5.ecotech.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kelompok5.ecotech.DataStoreManager
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.adapter.AdapterPenyetor
import com.kelompok5.ecotech.databinding.ActivityHomeKolektorBinding
import com.kelompok5.ecotech.model.DataItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeKolektorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeKolektorBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterPenyetor
    private var backPressedTime: Long = 0
    private lateinit var toast : Toast
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeKolektorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataStoreManager = DataStoreManager.getInstance(this)

        lifecycleScope.launch {
            val name = dataStoreManager.name.first().toString()
            binding.tvGreetings.text = "Halo, ${name}!"
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val sampleData = listOf(
            DataItem("Budiman", "Menunggu"),
            DataItem("Darsono", "Menunggu"),
            DataItem("Setya", "Menunggu")
        )
        adapter = AdapterPenyetor(sampleData)
        recyclerView.adapter = adapter

        binding.userProfile.setOnClickListener {
            startActivity(Intent(Intent(this, AccountInfoActivity::class.java)))
        }
        binding.settings.setOnClickListener {
            val intent = Intent(this, AccountInfoActivity::class.java)
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
package com.kelompok5.ecotech.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.adapter.AdapterPenyetor
import com.kelompok5.ecotech.databinding.ActivityHomeKolektorBinding
import com.kelompok5.ecotech.model.DataItem

class HomeKolektorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeKolektorBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterPenyetor
    private var backPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeKolektorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val sampleData = listOf(
            DataItem("Budiman", "Menunggu"),
            DataItem("Darsono", "Menunggu"),
            DataItem("Setya", "Menunggu")
        )
        adapter = AdapterPenyetor(sampleData)
        recyclerView.adapter = adapter
    }

    override fun onBackPressed() {
        if (backPressedOnce) {
            super.onBackPressed()
            return
        }
        this.backPressedOnce = true
        Toast.makeText(this, "Ketuk sekali lagi untuk keluar", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed({
            backPressedOnce = false
        }, 2000)
    }
}
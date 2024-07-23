package com.kelompok5.ecotech.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompok5.ecotech.DataStoreManager
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.adapter.AdapterListPenyetorByKolektorId
import com.kelompok5.ecotech.data.model.response.orders.orderDataByID
import com.kelompok5.ecotech.data.remote.ApiService
import com.kelompok5.ecotech.data.remote.RetrofitClient
import com.kelompok5.ecotech.databinding.ActivityHomeKolektorBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeKolektorActivity : AppCompatActivity(), AdapterListPenyetorByKolektorId.OnItemClickListener {
    private lateinit var binding: ActivityHomeKolektorBinding
    private lateinit var adapter: AdapterListPenyetorByKolektorId
    private var backPressedTime: Long = 0
    private lateinit var toast : Toast
    private lateinit var dataStoreManager: DataStoreManager
    private var kolektorId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStoreManager = DataStoreManager.getInstance(this)
        kolektorId = intent.getStringExtra(EXTRA_KOLEKTOR_ID)
        binding = ActivityHomeKolektorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            val name = dataStoreManager.name.firstOrNull()
            if (name.isNullOrEmpty()) {
                binding.tvGreetings.text = "Selamat datang!"
            } else {
                binding.tvGreetings.text = "Halo, $name!"
            }
        }
        binding.recyclerViewPenyetor.layoutManager = LinearLayoutManager(this)
        adapter = AdapterListPenyetorByKolektorId(mutableListOf(), this)
        binding.recyclerViewPenyetor.adapter = adapter

        binding.email.setOnClickListener{
            Toast.makeText(this, "Coming soon!", Toast.LENGTH_SHORT).show()
        }

        val ivRiwayat = findViewById<ImageView>(R.id.ivRiwayatPenyetor)
        ivRiwayat.setOnClickListener {
            val intent = Intent(this, HistoryOrderPenyetorPageActivity::class.java)
            startActivity(intent)
        }

        binding.btnRefresh.setOnClickListener {
            refreshData()
        }

        binding.userProfile.setOnClickListener {
            startActivity(Intent(Intent(this, AccountInfoActivity::class.java)))
        }
        binding.settings.setOnClickListener {
            val intent = Intent(this, AccountInfoActivity::class.java)
            startActivity(intent)
        }
        fetchPenyetorId()
    }

    override fun onStart() {
        super.onStart()
        kolektorId = intent.getStringExtra(EXTRA_KOLEKTOR_ID)
        fetchPenyetorId()
    }
    override fun onItemClick(penyetor: orderDataByID) {
        val intent = Intent(this, DetailPenyetorActivity::class.java).apply {
            putExtra("id", penyetor.id)
            putExtra("nama_penyetor", penyetor.penyetor_name)
            putExtra("image_url", penyetor.item_image)
            putExtra(DetailKolektorActivity.EXTRA_KOLEKTOR_ID, kolektorId)
        }
        startActivity(intent)
    }

    private fun refreshData() {
        // Show a loading message or a progress bar (optional)
        Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show()
        lifecycleScope.launch {
            val name = dataStoreManager.name.firstOrNull()
            binding.tvGreetings.text = "Halo $name"
        }
        // Fetch updated
        fetchPenyetorId()
    }

    private fun fetchPenyetorId() {
        lifecycleScope.launch(Dispatchers.IO) {
            kolektorId = dataStoreManager.id.firstOrNull()
            fetchData()
        }
    }

    private fun fetchData() {
        CoroutineScope(Dispatchers.IO).launch {
            val apiService = RetrofitClient.createService<ApiService>()
            try {
                val response = kolektorId?.let {
                    Log.d("HomeKolektorActivity", "Fetching data for kolektorId: $it")
                    apiService.getOrderEwasteByKolektorIdAndStatusMenunggu(
                        it
                    )
                }
                Log.d("HomeKolektorActivity", "Respond received: $response")
                withContext(Dispatchers.Main) {
                    if (response != null && response.data.isNotEmpty()) {
                        Log.d("HomeKolektorActivity", "Data received: ${response.data}")
                        adapter.updateData(response.data)
                    } else {
                        Log.d("HomeKolektorActivity", "No data received")
                        adapter.updateData(emptyList())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("ListKolektorActivity", "Error fetching data", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@HomeKolektorActivity, "Error fetching data", Toast.LENGTH_SHORT).show()
                }
            }
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

    companion object {
        const val EXTRA_ORDER_ID = "extra_order_id"
        const val EXTRA_KOLEKTOR_ID = "extra_kolektor_id"
        const val EXTRA_KOLEKTOR_NAME = "extra_kolektor_name"
    }
}
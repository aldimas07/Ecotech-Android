package com.kelompok5.ecotech.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kelompok5.ecotech.DataStoreManager
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.activities.ListKolektorActivity.Companion.EXTRA_PENYETOR_ID
import com.kelompok5.ecotech.adapter.AdapterListPenyetorByKolektorId
import com.kelompok5.ecotech.adapter.AdapterPenyetor
import com.kelompok5.ecotech.data.model.response.kolektor.allKolektor
import com.kelompok5.ecotech.data.model.response.orders.orderDataByID
import com.kelompok5.ecotech.data.remote.ApiService
import com.kelompok5.ecotech.data.remote.RetrofitClient
import com.kelompok5.ecotech.databinding.ActivityHomeKolektorBinding
import com.kelompok5.ecotech.model.DataItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeKolektorActivity : AppCompatActivity(), AdapterListPenyetorByKolektorId.OnItemClickListener {
    private lateinit var binding: ActivityHomeKolektorBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterListPenyetorByKolektorId
    private var backPressedTime: Long = 0
    private lateinit var toast : Toast
    private lateinit var dataStoreManager: DataStoreManager
    private var kolektorId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStoreManager = DataStoreManager.getInstance(this)
        kolektorId = intent.getStringExtra(EXTRA_KOLEKTOR_ID)

        lifecycleScope.launch {
            val name = dataStoreManager.name.firstOrNull() ?: "!"
            binding.tvGreetings.text = "Halo, ${name}!"
        }
        binding = ActivityHomeKolektorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.email.setOnClickListener{
            Toast.makeText(this, "Coming soon!", Toast.LENGTH_SHORT).show()
        }

        binding.recyclerViewPenyetor.layoutManager = LinearLayoutManager(this)
        adapter = AdapterListPenyetorByKolektorId(mutableListOf(), this)
        binding.recyclerViewPenyetor.adapter = adapter

        binding.userProfile.setOnClickListener {
            startActivity(Intent(Intent(this, AccountInfoActivity::class.java)))
        }
        binding.settings.setOnClickListener {
            val intent = Intent(this, AccountInfoActivity::class.java)
            startActivity(intent)
        }
        fetchData()
        fetchPenyetorId()
    }

    override fun onItemClick(penyetor: orderDataByID) {
        val intent = Intent(this, DetailPenyetorActivity::class.java).apply {
            putExtra("nama_penyetor", penyetor.penyetor_name)
            putExtra("image_url", penyetor.item_image)
            putExtra(DetailKolektorActivity.EXTRA_KOLEKTOR_ID, kolektorId)
        }
        startActivity(intent)
    }

    private fun fetchPenyetorId() {
        CoroutineScope(Dispatchers.IO).launch {
            kolektorId = dataStoreManager.id.firstOrNull()
            withContext(Dispatchers.Main) {
                fetchData()
            }
        }
    }

    private fun fetchData() {
        CoroutineScope(Dispatchers.IO).launch {
            val apiService = RetrofitClient.createService<ApiService>()
            try {
                //ambil kolektorid dari datastore
                val response = kolektorId?.let {
                    apiService.getOrderEwasteByKolektorIdAndStatusMenunggu(
                        it
                    )
                }
                Log.d("HomeKolektorActivity", "Respond received: $response")
                if (response != null) {
                    withContext(Dispatchers.Main) {
                        Log.d("HomeKolektorActivity", "Data received: ${response.data}")
                        if (response.data.isNotEmpty()) {
                            adapter.updateData(response.data)
                        } else {
                            Log.d("HomeKolektorActivity", "No data received")
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("ListKolektorActivity", "Error fetching data", e)
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
        const val EXTRA_KOLEKTOR_ID = "extra_kolektor_id"
        const val EXTRA_KOLEKTOR_NAME = "extra_kolektor_name"
    }
}
package com.kelompok5.ecotech.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kelompok5.ecotech.DataStoreManager
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.activities.HomeKolektorActivity.Companion.EXTRA_KOLEKTOR_ID
import com.kelompok5.ecotech.adapter.AdapterListPenyetorByKolektorId
import com.kelompok5.ecotech.adapter.AdapterRiwayatOrder
import com.kelompok5.ecotech.data.model.response.orders.orderDataByID
import com.kelompok5.ecotech.data.model.response.orders.orderDataByIDPenyetor
import com.kelompok5.ecotech.data.remote.ApiService
import com.kelompok5.ecotech.data.remote.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryOrderKolektorPageActivity : AppCompatActivity(), AdapterRiwayatOrder.OnItemClickListener {
   private lateinit var recyclerView: RecyclerView
   private lateinit var adapter: AdapterRiwayatOrder
   private lateinit var dataStoreManager: DataStoreManager
   private var penyetorId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_order_kolektor_page)

        recyclerView = findViewById(R.id.recyclerViewKolektor)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = AdapterRiwayatOrder(mutableListOf(), this)
        recyclerView.adapter = adapter

        dataStoreManager = DataStoreManager.getInstance(this)
        fetchKolektorId()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onItemClick(kolektor: orderDataByIDPenyetor) {
        val intent = Intent(this, DetailHistoryOrderPenyetorActivity::class.java).apply {
            putExtra("id", kolektor.id)
            putExtra("nama_kolektor", kolektor.kolektor_name)
            putExtra("alamat", kolektor.alamat)
            putExtra("nohp", kolektor.nohp)
            putExtra("image_url", kolektor.item_image)
            putExtra("status", kolektor.status)
            putExtra(DetailHistoryOrderPenyetorActivity.EXTRA_PENYETOR_ID, penyetorId)
        }
        startActivity(intent)
    }

    private fun fetchKolektorId() {
        lifecycleScope.launch(Dispatchers.IO) {
            penyetorId = dataStoreManager.id.firstOrNull()
            fetchData()
        }
    }

    private fun fetchData() {
        CoroutineScope(Dispatchers.IO).launch {
            val apiService = RetrofitClient.createService<ApiService>()
            try {
                val response = penyetorId?.let {
                    Log.d("HistoryOrderKolektorPage", "Fetching data for penyetorId: $it")
                    apiService.getAllStatusOrderEwasteByPenyetorId(
                        it
                    )
                }
                Log.d("HistoryOrderPenyetorPage", "Respond received: $response")
                withContext(Dispatchers.Main) {
                    if (response != null && response.data.isNotEmpty()) {
                        Log.d("HistoryOrderKolektorPage", "Data received: ${response.data}")
                        adapter.updateData(response.data)
                    } else {
                        Log.d("HistoryOrderKolektorPage", "No data received")
                        adapter.updateData(emptyList())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("HistoryOrderKolektorPage", "Error fetching data", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@HistoryOrderKolektorPageActivity, "Error fetching data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
    }

}
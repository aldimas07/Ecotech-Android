package com.kelompok5.ecotech.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
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
import com.kelompok5.ecotech.adapter.AdapterListKolektor
import com.kelompok5.ecotech.adapter.AdapterListPenyetorByKolektorId
import com.kelompok5.ecotech.data.model.response.orders.orderDataByID
import com.kelompok5.ecotech.data.remote.ApiService
import com.kelompok5.ecotech.data.remote.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryOrderPenyetorPageActivity : AppCompatActivity(), AdapterListPenyetorByKolektorId.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterListPenyetorByKolektorId
    private lateinit var dataStoreManager: DataStoreManager
    private var kolektorId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_order_penyetor_page)

        recyclerView = findViewById(R.id.recyclerViewPenyetor)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = AdapterListPenyetorByKolektorId(mutableListOf(), this)
        recyclerView.adapter = adapter

        dataStoreManager = DataStoreManager.getInstance(this)

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
                    Log.d("HistoryOrderPenyetorPage", "Fetching data for kolektorId: $it")
                    apiService.getAllStatusOrderEwasteByKolektorId(
                        it
                    )
                }
                Log.d("HistoryOrderPenyetorPage", "Respond received: $response")
                withContext(Dispatchers.Main) {
                    if (response != null && response.data.isNotEmpty()) {
                        Log.d("HistoryOrderPenyetorPage", "Data received: ${response.data}")
                        adapter.updateData(response.data)
                    } else {
                        Log.d("HistoryOrderPenyetorPage", "No data received")
                        adapter.updateData(emptyList())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("ListKolektorActivity", "Error fetching data", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@HistoryOrderPenyetorPageActivity, "Error fetching data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_PENYETOR_ID = "extra_penyetor_id"
    }
}
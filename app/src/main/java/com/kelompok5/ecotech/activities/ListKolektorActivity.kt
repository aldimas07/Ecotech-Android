package com.kelompok5.ecotech.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kelompok5.ecotech.DataStoreManager
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.adapter.AdapterListKolektor
import com.kelompok5.ecotech.data.model.response.kolektor.allKolektor
import com.kelompok5.ecotech.data.remote.ApiService
import com.kelompok5.ecotech.data.remote.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListKolektorActivity : AppCompatActivity(), AdapterListKolektor.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterListKolektor
    private var imageUri: String? = null
    private lateinit var dataStoreManager: DataStoreManager
    private var penyetorId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_kolektor)

        imageUri = intent.getStringExtra(EXTRA_IMAGE_URI)
        penyetorId = intent.getStringExtra(EXTRA_PENYETOR_ID)

        recyclerView = findViewById(R.id.recyclerViewKolektor)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = AdapterListKolektor(mutableListOf(), this)
        recyclerView.adapter = adapter


        dataStoreManager = DataStoreManager.getInstance(this)

        fetchData()
        fetchPenyetorId()
    }

    override fun onItemClick(kolektor: allKolektor) {
        val intent = Intent(this, DetailKolektorActivity::class.java)
        intent.putExtra("kolektor", kolektor)
        intent.putExtra(DetailKolektorActivity.EXTRA_IMAGE_URI, imageUri)
        intent.putExtra(DetailKolektorActivity.EXTRA_PENYETOR_ID, penyetorId)
        intent.putExtra(DetailKolektorActivity.EXTRA_KOLEKTOR_ID, kolektor.id)
        startActivity(intent)
    }

    private fun fetchPenyetorId() {
        CoroutineScope(Dispatchers.IO).launch {
            penyetorId = dataStoreManager.id.firstOrNull()
            withContext(Dispatchers.Main) {
                fetchData()
            }
        }
    }

    private fun fetchData() {
        CoroutineScope(Dispatchers.IO).launch {
            val apiService = RetrofitClient.createService<ApiService>()
            try {
                val response = apiService.getAllKolektor()
                Log.d("ListKolektorActivity", "Respond received: $response")
                withContext(Dispatchers.Main) {
                    Log.d("ListKolektorActivity", "Data received: ${response.data}")
                    if (response.data.isNotEmpty()) {
                        adapter.updateData(response.data)
                    } else {
                        Log.d("ListKolektorActivity", "No data received")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("ListKolektorActivity", "Error fetching data", e)
            }
        }
    }
    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_PENYETOR_ID = "extra_penyetor_id"
    }
}

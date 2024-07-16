package com.kelompok5.ecotech.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.adapter.AdapterListKolektor
import com.kelompok5.ecotech.data.model.response.kolektor.allKolektor
import com.kelompok5.ecotech.data.remote.ApiService
import com.kelompok5.ecotech.data.remote.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListKolektorActivity : AppCompatActivity(), AdapterListKolektor.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterListKolektor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_kolektor)

        recyclerView = findViewById(R.id.recyclerViewKolektor)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = AdapterListKolektor(mutableListOf(), this)
        recyclerView.adapter = adapter

        fetchData()
    }

    override fun onItemClick(kolektor: allKolektor) {
        val intent = Intent(this, DetailKolektorActivity::class.java)
        intent.putExtra("kolektor", kolektor)
        startActivity(intent)
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
}

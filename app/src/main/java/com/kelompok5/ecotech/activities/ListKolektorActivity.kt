package com.kelompok5.ecotech.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.adapter.AdapterListKolektor
import com.kelompok5.ecotech.model.KolektorModel

class ListKolektorActivity : AppCompatActivity(), AdapterListKolektor.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterListKolektor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_kolektor)

        recyclerView = findViewById(R.id.recyclerViewKolektor)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val kolektorList = listOf(
            KolektorModel("PT. Best Profit Futures", "Malang"),
            KolektorModel("Agen e-waste RJ Surabaya", "Wonorejo, Rungkut, Surabaya"),
            KolektorModel("Bank Sampah Kosahi", "Jl. Perusahaan, Gg. III No.26, Losawi, Tanjungtirto, Kec. Singosari, Kabupaten Malang, Jawa Timur 65153")
        )

        adapter = AdapterListKolektor(kolektorList, this)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(kolektor: KolektorModel) {
        val intent = Intent(this, DetailKolektorActivity::class.java)
        intent.putExtra("kolektor", kolektor)
        startActivity(intent)
    }
}

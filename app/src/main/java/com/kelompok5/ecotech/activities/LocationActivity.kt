package com.kelompok5.ecotech.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompok5.ecotech.adapter.AdapterLokasi
import com.kelompok5.ecotech.databinding.ActivityLocationBinding
import com.kelompok5.ecotech.model.ItemLokasi
import java.util.ArrayList

class LocationActivity : AppCompatActivity() {

    private lateinit var mArrayList: ArrayList<ItemLokasi>
    private lateinit var binding: ActivityLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mArrayList = ArrayList()
        binding.listLokasi.layoutManager = LinearLayoutManager(this)
        binding.listLokasi.setHasFixedSize(true)
        binding.listLokasi.adapter = AdapterLokasi(mArrayList)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        getItemLokasi()
    }

    private fun getItemLokasi() {
        mArrayList.add(ItemLokasi("Otaku Playground", "Akihabara"))
        mArrayList.add(ItemLokasi("Transmart", "Tegal"))
        mArrayList.add(ItemLokasi("Otaku Playground", "Akihabara"))
        mArrayList.add(ItemLokasi("Transmart", "Tegal"))
        mArrayList.add(ItemLokasi("Otaku Playground", "Akihabara"))
        mArrayList.add(ItemLokasi("Transmart", "Tegal"))
        mArrayList.add(ItemLokasi("Otaku Playground", "Akihabara"))
        mArrayList.add(ItemLokasi("Transmart", "Tegal"))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
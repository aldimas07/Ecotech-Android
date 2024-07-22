package com.kelompok5.ecotech.activities

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.data.model.response.orders.orderDataByID
import com.kelompok5.ecotech.databinding.ActivityDetailPenyetorBinding

class DetailPenyetorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPenyetorBinding
    private lateinit var tvDetailNamaPenyetor: TextView
    private lateinit var imgItem: ImageView
    private lateinit var btnTerima: Button
    private lateinit var btnTolak: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPenyetorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvDetailNamaPenyetor = findViewById(R.id.tvDetailNamaPenyetor)
        imgItem = findViewById(R.id.IvDetailGambar)
        btnTerima = findViewById(R.id.btnTerimaOrderPenyetor)
        btnTolak = findViewById(R.id.btnTolakOrderPenyetor)

        val namaPenyetor = intent.getStringExtra("nama_penyetor")
        val itemImageUrl = intent.getStringExtra("image_url")

        if (namaPenyetor != null) {
            tvDetailNamaPenyetor.text = namaPenyetor
        }

        if (itemImageUrl != null) {
            Glide.with(this).load(itemImageUrl).into(imgItem)
        }

        btnTerima.setOnClickListener {
            // Handle accept action
        }

        btnTolak.setOnClickListener {
            // Handle reject action
        }
    }
}
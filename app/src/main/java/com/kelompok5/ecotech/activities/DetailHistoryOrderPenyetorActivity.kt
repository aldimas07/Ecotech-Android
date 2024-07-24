package com.kelompok5.ecotech.activities

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.kelompok5.ecotech.DataStoreManager
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.activities.DetailKolektorActivity.Companion.EXTRA_IMAGE_URI
import com.kelompok5.ecotech.data.model.response.kolektor.allKolektor
import com.kelompok5.ecotech.data.model.response.orders.orderDataByID
import com.kelompok5.ecotech.data.model.response.orders.orderDataByIDPenyetor
import com.kelompok5.ecotech.databinding.ActivityDetailHistoryOrderPenyetorBinding
import com.kelompok5.ecotech.databinding.ActivityDetailKolektorBinding
import com.kelompok5.ecotech.databinding.ActivityDetailPenyetorBinding
import com.kelompok5.ecotech.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailHistoryOrderPenyetorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailHistoryOrderPenyetorBinding
    private lateinit var tvDetailNamaKolektor: TextView
    private lateinit var tvDetailAlamatKolektor: TextView
    private lateinit var tvDetailNoHpKolektor: TextView
    private lateinit var tvDetailStatus: TextView
    private lateinit var imgItem: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryOrderPenyetorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvDetailNamaKolektor = findViewById(R.id.tvDetailNamaKolektor)
        tvDetailAlamatKolektor = findViewById(R.id.tvDetailAlamatKolektor)
        tvDetailNoHpKolektor = findViewById(R.id.tvDetailNohp)
        imgItem = findViewById(R.id.IvDetailGambar)
        tvDetailStatus = findViewById(R.id.tvDetailStatus)

        val namaKolektor = intent.getStringExtra("nama_kolektor")
        val alamatKolektor = intent.getStringExtra("alamat")
        val noHpKolektor = intent.getStringExtra("nohp")
        val itemImageUrl = intent.getStringExtra("image_url")
        val status = intent.getStringExtra("status")

        if (namaKolektor != null) {
            tvDetailNamaKolektor.text = namaKolektor
        }
        if (alamatKolektor != null) {
            tvDetailAlamatKolektor.text = alamatKolektor
        }
        if (noHpKolektor != null) {
            tvDetailNoHpKolektor.text = noHpKolektor
        }
        if (itemImageUrl != null) {
            Glide.with(this).load(itemImageUrl).into(imgItem)
        }
        if (status != null) {
            tvDetailStatus.text = status
        }
    }

    companion object {
        const val EXTRA_PENYETOR_ID = "extra_penyetor_id"
        const val EXTRA_KOLEKTOR_ID = "extra_kolektor_id"
        const val EXTRA_IMAGE_URI = "extra_image_uri"
    }
}
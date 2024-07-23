package com.kelompok5.ecotech.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.databinding.ActivityDetailPenyetorBinding
import com.kelompok5.ecotech.viewmodel.OrderViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailPenyetorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPenyetorBinding
    private lateinit var tvDetailNamaPenyetor: TextView
    private lateinit var imgItem: ImageView
    private lateinit var btnTerima: Button
    private lateinit var btnTolak: Button
    private val orderViewModel: OrderViewModel by viewModel()

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

        orderViewModel.updateResponse.observe(this, Observer { response ->
            if (response.statusCode == 200) {
                Toast.makeText(this, "Status order berhasil diubah", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "Mohon tunggu 1-3 menit untuk melihat perubahan data.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Terjadi kesalahan dalam mengubah status order", Toast.LENGTH_SHORT).show()
            }
            finish()
        })

        btnTerima.setOnClickListener {
            showConfirmationDialogAccept()

        }

        btnTolak.setOnClickListener {
            showConfirmationDialogReject()
        }
    }

    private fun acceptUpdate() {
        val orderId = intent.getStringExtra("id")
        if (orderId != null) {
            orderViewModel.updateStatusOrderEwasteAccepted(orderId)
        } else {
            Toast.makeText(this, "Invalid data.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun rejectUpdate() {
        val orderId = intent.getStringExtra("id")
        if (orderId != null) {
            orderViewModel.updateStatusOrderReject(orderId)
        } else {
            Toast.makeText(this, "Invalid data.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showConfirmationDialogAccept() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi")
            .setMessage("Kamu akan menyetujui agar e-waste milik penyetor ini akan dikirim ke alamatmu. Apakah kamu menerima?")
            .setPositiveButton("Terima") { dialog, which ->
                acceptUpdate()
            }
            .setNegativeButton("Kembali", null)
            .show()
    }


    private fun showConfirmationDialogReject() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi")
            .setMessage("Kamu akan menolak e-waste milik penyetor ini untuk dikirim ke alamatmu. Apakah kamu mau menolak?")
            .setPositiveButton("Tolak") { dialog, which ->
                rejectUpdate()
            }
            .setNegativeButton("Kembali", null)
            .show()
    }
}
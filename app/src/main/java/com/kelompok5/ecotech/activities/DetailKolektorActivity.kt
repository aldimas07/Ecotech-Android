package com.kelompok5.ecotech.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.model.KolektorModel

class DetailKolektorActivity : AppCompatActivity() {

    private lateinit var tvNamaKolektor: TextView
    private lateinit var tvAlamatKolektor: TextView
    private lateinit var btnSerahkan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_kolektor)

        tvNamaKolektor = findViewById(R.id.tvDetailNamaKolektor)
        tvAlamatKolektor = findViewById(R.id.tvDetailAlamatKolektor)
        btnSerahkan = findViewById(R.id.btnSerahkan)

        val kolektor = intent.getParcelableExtra<KolektorModel>("kolektor")
        kolektor?.let {
            tvNamaKolektor.text = "${it.nama}"
            tvAlamatKolektor.text = "${it.alamat}"
        }

        btnSerahkan.setOnClickListener {
            // submit action
        }
    }
}

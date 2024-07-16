package com.kelompok5.ecotech.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.data.model.response.kolektor.allKolektor

class DetailKolektorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_kolektor)

        // Ambil data dari Intent
        val kolektor = intent.getParcelableExtra<allKolektor>("kolektor")

        // Tampilkan data kolektor (contoh)
        if (kolektor != null) {
            findViewById<TextView>(R.id.tvDetailNamaKolektor).text = kolektor.name
            findViewById<TextView>(R.id.tvDetailAlamatKolektor).text = kolektor.alamat
            findViewById<TextView>(R.id.tvDetailNohp).text = kolektor.nohp
        }
    }
}

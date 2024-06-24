package com.kelompok5.ecotech.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prediction = intent.getStringExtra(EXTRA_PREDICT)


        if (prediction == "This is an e-waste, you can send it to the collector!") {
            binding.tvHasil.text = "Bisa Didaur Ulang"
            binding.tvPerintah.text = "Sampah bisa didaur ulang. Silahkan berikan ke pengepulan terdekat"
            binding.btnLokasi.setOnClickListener {
                startActivity(Intent(Intent(this, MapsActivity::class.java)))
            }
        } else {
            binding.ivTrue.setImageResource(R.drawable.close)
            binding.btnLokasi.text = "Kembali"
            binding.btnLokasi.setOnClickListener {
                finish()
            }
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_PREDICT = "extra_predict"
        const val EXTRA_URI = "extra_uri"
    }
}
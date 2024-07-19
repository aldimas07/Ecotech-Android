package com.kelompok5.ecotech.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private var getUri: Uri? = null
    private var penyetorId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUri = Uri.parse(intent.getStringExtra(EXTRA_URI))

        binding.tvHasil.text = "Bisa Didaur Ulang"
        binding.tvPerintah.text = "Yay! Sampah bisa didaur ulang. \n Yuk, kumpulkan ke pengepulan terdekat!"
        binding.btnLokasi.setOnClickListener {
            navigateToListKolektorActivity()
        }

        val prediction = intent.getStringExtra(EXTRA_PREDICT)


//        if (prediction == "This is an e-waste, you can send it to the collector!") {
//            binding.tvHasil.text = "Bisa Didaur Ulang"
//            binding.tvPerintah.text = "Yay! Sampah bisa didaur ulang. \n Yuk, kumpulkan ke pengepulan terdekat!"
//            binding.btnLokasi.setOnClickListener {
//                startActivity(Intent(Intent(this, ListKolektorActivity::class.java)))
//            }
//        } else {
//            binding.tvHasil.text = "Tidak Bisa Didaur Ulang"
//            binding.tvPerintah.text = "Maaf, sistem tidak dapat mendeteksi. Silakan ubah angle fotonya atau cari e-waste lain!"
//            binding.ivTrue.setImageResource(R.drawable.close)
//            binding.btnLokasi.text = "Kembali"
//            binding.btnLokasi.setOnClickListener {
//                finish()
//            }
//        }

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

    }

    private fun navigateToListKolektorActivity() {
        val intent = Intent(this, ListKolektorActivity::class.java)
        intent.putExtra(ListKolektorActivity.EXTRA_IMAGE_URI, getUri.toString())
        intent.putExtra(ListKolektorActivity.EXTRA_PENYETOR_ID, penyetorId)
        startActivity(intent)
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
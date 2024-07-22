package com.kelompok5.ecotech.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

        val uriString = intent.getStringExtra(EXTRA_URI)
        getUri = if (uriString != null) Uri.parse(uriString) else null

        val prediction = intent.getStringExtra(EXTRA_PREDICT)

        if (getUri == null) {
            Log.e("ResultActivity", "Received null URI")
            Toast.makeText(this, "Invalid image URI", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        Log.d("ResultActivity", "URI received: $getUri")
        Log.d("ResultActivity", "Prediction received: $prediction")

        if (prediction == "This is an e-waste, you can send it to the collector!") {
            binding.tvHasil.text = "Bisa Didaur Ulang"
            binding.tvPerintah.text = "Yay! Sampah bisa didaur ulang. \n Yuk, kumpulkan ke pengepulan terdekat!"
            binding.btnLokasi.setOnClickListener {
                navigateToListKolektorActivity()
            }
        } else {
            binding.tvHasil.text = "Tidak Bisa Didaur Ulang"
            binding.tvPerintah.text = "Maaf, sistem tidak dapat mendeteksi. Mohon ubah angle fotonya atau cari e-waste lain!"
            binding.ivTrue.setImageResource(R.drawable.close)
            binding.btnLokasi.text = "Kembali"
            binding.btnLokasi.setOnClickListener {
                finish()
                finishAndRemoveTask()
            }
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

    }

    private fun navigateToListKolektorActivity() {
        val intent = Intent(this, ListKolektorActivity::class.java)
        getUri?.let { uri ->
            intent.putExtra(ListKolektorActivity.EXTRA_IMAGE_URI, uri.toString())
            intent.putExtra(ListKolektorActivity.EXTRA_PENYETOR_ID, penyetorId)
            startActivity(intent)
        }  ?: run {
            Log.e("ResultActivity", "Cannot navigate, URI is null")
            Toast.makeText(this, "Invalid image URI", Toast.LENGTH_SHORT).show()
        }
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
package com.kelompok5.ecotech.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.kelompok5.ecotech.DataStoreManager
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.data.model.response.kolektor.allKolektor
import com.kelompok5.ecotech.reduceFileImage
import com.kelompok5.ecotech.uriToFile
import com.kelompok5.ecotech.viewmodel.MainViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailKolektorActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_kolektor)

        dataStoreManager = DataStoreManager.getInstance(this)

        val uriString = intent.getStringExtra(EXTRA_IMAGE_URI)
        val imageUri = if (uriString != null) Uri.parse(uriString) else null

        if (imageUri == null) {
            Log.e("DetailKolektorActivity", "Received null image URI")
            Toast.makeText(this, "Invalid image URI", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val btnSerahkan = findViewById<Button>(R.id.btnSerahkan)
        val kolektor = intent.getParcelableExtra<allKolektor>("kolektor")

        // Tampilkan data kolektor (contoh)
        if (kolektor != null) {
            findViewById<TextView>(R.id.tvDetailNamaKolektor).text = kolektor.name
            findViewById<TextView>(R.id.tvDetailAlamatKolektor).text = kolektor.alamat
            findViewById<TextView>(R.id.tvDetailNohp).text = kolektor.nohp
        }
        btnSerahkan.setOnClickListener {
            showConfirmationDialog()
        }
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi")
            .setMessage("Kamu akan menyetorkan e-waste milikmu ke kolektor ini. Apakah kamu setuju?")
            .setPositiveButton("Setuju") { dialog, which ->
                createOrderAndUploadImage()
                startActivity(Intent(this, SuccessCreateOrderActivity::class.java))
            }
            .setNegativeButton("Tidak Setuju", null)
            .show()
    }

    private fun createOrderAndUploadImage() {
        val penyetorId = intent.getStringExtra(EXTRA_PENYETOR_ID)
        val kolektorId = intent.getStringExtra(EXTRA_KOLEKTOR_ID)
        val imageUriString = intent.getStringExtra(EXTRA_IMAGE_URI)

        Log.d("PenyetorCR", "penyetorId:$penyetorId")
        Log.d("KolektorCR", "kolektorId:$kolektorId")
        Log.d("ImageUri", "imageUriString:$imageUriString")
        if (penyetorId != null && kolektorId != null && imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            val imageFile = uriToFile(imageUri, this)
            val reducedFile = reduceFileImage(imageFile)

            val requestImageFile = reducedFile.asRequestBody("image/jpeg".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image",
                reducedFile.name,
                requestImageFile
            )
            val penyetorIdBody = penyetorId.toRequestBody("text/plain".toMediaTypeOrNull())
            val kolektorIdBody = kolektorId.toRequestBody("text/plain".toMediaTypeOrNull())

            mainViewModel.createOrdersEwaste(penyetorIdBody, kolektorIdBody, imageMultipart).observe(this) { response ->
                if (response.statusCode == 201) {
                    Toast.makeText(this, "Order created successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to create order.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Invalid data.", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val EXTRA_PENYETOR_ID = "extra_penyetor_id"
        const val EXTRA_KOLEKTOR_ID = "extra_kolektor_id"
        const val EXTRA_IMAGE_URI = "extra_image_uri"
    }
}

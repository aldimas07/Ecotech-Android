package com.irhamsoetomo.ecotech.activities

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.irhamsoetomo.ecotech.R
import com.irhamsoetomo.ecotech.createCustomTempFile
import com.irhamsoetomo.ecotech.databinding.ActivityMainBinding
import com.irhamsoetomo.ecotech.fragment.FragmentHome
import com.irhamsoetomo.ecotech.fragment.FragmentStruk
import com.irhamsoetomo.ecotech.reduceFileImage
import com.irhamsoetomo.ecotech.uriToFile
import com.irhamsoetomo.ecotech.viewmodel.MainViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class MainActivity : AppCompatActivity() {
    private var getFile: File? = null
    private var getUri: Uri? = null

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)

            myFile.let { file ->
//          Silakan gunakan kode ini jika mengalami perubahan rotasi
//          rotateFile(file)
                getFile = file
                uploadImage()
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri

            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@MainActivity)
                getFile = myFile
                uploadImage()
            }
        }
    }

    private fun uploadImage() {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)

            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image",
                file.name,
                requestImageFile
            )
            mainViewModel.predict(imageMultipart)
        } else {
            Toast.makeText(
                this@MainActivity,
                "Silakan masukkan berkas gambar terlebih dahulu.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        switchFragment(FragmentHome())

        binding.fab.setOnClickListener {
            showDialogFab();
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> switchFragment(FragmentHome())
                R.id.snippet -> switchFragment(FragmentStruk())
                else -> {

                }
            }
            true
        }

        observePrediction()
    }

    private fun showDialogFab() {
        val customDialog = Dialog(this)
        customDialog.setContentView(R.layout.dialog_fab)
        customDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        customDialog.window?.setBackgroundDrawableResource(R.color.transparent)
        customDialog.setCancelable(true)
        val kamera = customDialog.findViewById<LinearLayout>(R.id.kamera)
        val galeri = customDialog.findViewById<LinearLayout>(R.id.galeri)
        kamera.setOnClickListener {
            // Buka Kamera
            startTakePhoto()
            customDialog.hide()
        }
        galeri.setOnClickListener {
            // Buka Galeri
            startGallery()
            customDialog.hide()
        }
        customDialog.show()
    }

    private fun observePrediction() {
        mainViewModel.prediction.observe(this) { predictionResponse ->
            if (predictionResponse != null) {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra(ResultActivity.EXTRA_PREDICT, predictionResponse.prediction)
                intent.putExtra(ResultActivity.EXTRA_URI, getUri.toString())
                startActivity(intent)
            }
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@MainActivity,
                "com.irhamsoetomo.ecotech",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun switchFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_contaimer, fragment)
        fragmentTransaction.commit()
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}
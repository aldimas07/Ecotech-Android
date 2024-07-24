package com.kelompok5.ecotech.activities

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.createCustomTempFile
import com.kelompok5.ecotech.databinding.ActivityMainBinding
import com.kelompok5.ecotech.fragment.FragmentHome
import com.kelompok5.ecotech.fragment.FragmentStruk
import com.kelompok5.ecotech.reduceFileImage
import com.kelompok5.ecotech.uriToFile
import com.kelompok5.ecotech.viewmodel.MainViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class MainActivity : AppCompatActivity() {
    private var getFile: File? = null
    private var getUri: Uri? = null
    private var penyetorId: String? = null

    private var backPressedTime: Long = 0
    private lateinit var toast : Toast

    private var customDialog: Dialog? = null

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
                getUri = uri
                uploadImage()
            } ?: run {
                Toast.makeText(this, "Failed to get image from gallery", Toast.LENGTH_SHORT).show()
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
            showFilterDialog()
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

//        observePrediction()
    }

    private fun showFilterDialog() {
        val dialog = Dialog(this)
        dialog.apply {
            setContentView(R.layout.dialog_filter_confirmation)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            window?.setBackgroundDrawableResource(R.color.transparent)
            setCancelable(true)

            val btnYes = findViewById<Button>(R.id.btnYes)
            val btnNo = findViewById<Button>(R.id.btnNo)

            btnYes.setOnClickListener {
                mainViewModel.prediction.observe(this@MainActivity) { predictionResponse ->
                    if (predictionResponse != null) {
                        val intent = Intent(this@MainActivity, ResultActivity::class.java)
                        intent.putExtra(ResultActivity.EXTRA_PREDICT, predictionResponse.prediction)
                        intent.putExtra(ResultActivity.EXTRA_URI, getUri.toString())
                        startActivity(intent)
                        dialog.dismiss()
                    }
                }
//                val intent = Intent(this@MainActivity, ResultActivity::class.java)
//                intent.putExtra(ResultActivity.EXTRA_PREDICT, mainViewModel.prediction.value?.prediction)
//                intent.putExtra(ResultActivity.EXTRA_URI, getUri.toString())
//                startActivity(intent)
//                dialog.dismiss()
            }

            btnNo.setOnClickListener {
                val intent = Intent(this@MainActivity, ListKolektorActivity::class.java)
                intent.putExtra(ResultActivity.EXTRA_URI, getUri.toString())
                getUri?.let { uri ->
                    intent.putExtra(ListKolektorActivity.EXTRA_IMAGE_URI, uri.toString())
                    intent.putExtra(ListKolektorActivity.EXTRA_PENYETOR_ID, penyetorId)
                    startActivity(intent)
                    dialog.dismiss()
                }  ?: run {
                    Log.e("ResultActivity", "Cannot navigate, URI is null")
                }
            }

            show()
        }
    }


    private fun showDialogFab() {
        // Inisialisasi customDialog jika belum ada
        if (customDialog == null) {
            customDialog = Dialog(this)
        }

        customDialog?.apply {
            setContentView(R.layout.dialog_fab)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            window?.setBackgroundDrawableResource(R.color.transparent)
            setCancelable(true)

            val kamera = findViewById<LinearLayout>(R.id.kamera)
            val galeri = findViewById<LinearLayout>(R.id.galeri)

            kamera.setOnClickListener {
                // Buka Kamera
                startTakePhoto()
                dismiss() // Gunakan dismiss() untuk menghapus dialog
            }
            galeri.setOnClickListener {
                // Buka Galeri
                startGallery()
                dismiss() // Gunakan dismiss() untuk menghapus dialog
            }

            show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        customDialog?.dismiss()
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
                "com.kelompok5.ecotech",
                it
            )
            currentPhotoPath = it.absolutePath
            getUri = photoURI
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
        const val EXTRA_PENYETOR_NAME = "extra_penyetor_name"
        const val EXTRA_PREDICT = "extra_predict"
        const val EXTRA_URI = "extra_uri"
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            toast.cancel()
            super.onBackPressed()
            finishAffinity()
        } else {
            toast = Toast.makeText(this, "Ketuk sekali lagi untuk keluar dari aplikasi!", Toast.LENGTH_SHORT)
            toast.show()
        }
        backPressedTime = System.currentTimeMillis()
    }


}

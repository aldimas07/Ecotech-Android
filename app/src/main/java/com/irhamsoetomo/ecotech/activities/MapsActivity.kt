package com.irhamsoetomo.ecotech.activities

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.irhamsoetomo.ecotech.R
import com.irhamsoetomo.ecotech.databinding.ActivityMapsBinding
import com.irhamsoetomo.ecotech.model.ItemLokasi

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val boundsBuilder = LatLngBounds.Builder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        getMyLocation()
        addManyMarker()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun addManyMarker() {
        val listLokasi = listOf(
            ItemLokasi(
                "Alang-alang zero waste store",
                "Mulyorejo, Surabaya, Jawa Timur 60115",
                -7.3894339,
                112.3230191
            ),
            ItemLokasi(
                "Agen EwasteRJ Surabaya",
                "Wonorejo, Rungkur, Surabaya",
                -7.2516405,
                112.7317752
            ),
            ItemLokasi("Agen EwasteRJ Mojokerto", "Sooko, Mojokerto", -7.5367978, 112.3882118),
            ItemLokasi(
                "Bank Sampah Kosahi",
                "Jl. Perusahaan, Gg. III No.26, Losawi, Tanjungtirto, Kec. Singosari, Kabupaten Malang, Jawa Timur 65153",
                -7.6313502,
                112.2920859
            ),
            ItemLokasi(
                "Bank Sampah Malang",
                "Jl. S. Supriadi No.38 A, Sukun, Kec. Sukun, Kota Malang, Jawa Timur 65147",
                -7.9813565,
                112.6058613
            ),
        )
        listLokasi.forEach { lokasi ->
            val latLng = LatLng(lokasi.latitude!!.toDouble(), lokasi.longitude!!.toDouble())
            mMap.addMarker(MarkerOptions().position(latLng).title(lokasi.nama))
            boundsBuilder.include(latLng)
        }

        val bounds: LatLngBounds = boundsBuilder.build()
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                300
            )
        )
    }
}
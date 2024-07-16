package com.kelompok5.ecotech.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.kelompok5.ecotech.DataStoreManager
import com.kelompok5.ecotech.EcotechApp
import com.kelompok5.ecotech.activities.AccountInfoActivity
import com.kelompok5.ecotech.databinding.FragmentHomeBinding
import com.kelompok5.ecotech.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val authViewModel: AuthViewModel by viewModel()
    private lateinit var dataStoreManager: DataStoreManager
    private var backPressedTime: Long = 0
    private lateinit var toast : Toast
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userProfile.setOnClickListener {
            startActivity(Intent(Intent(requireActivity(), AccountInfoActivity::class.java)))
        }

        dataStoreManager = DataStoreManager.getInstance(requireContext())

        lifecycleScope.launch {
            val name = dataStoreManager.name.first()
            binding.tvGreetings.text = "Hai, ${name}!"
        }

        binding.settings.setOnClickListener {
            val intent = Intent(requireActivity(), AccountInfoActivity::class.java)
            startActivity(intent)
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val currentTime = System.currentTimeMillis()
            if (currentTime - backPressedTime < 2000) {
                finishAffinity(requireActivity())
            }
        }
    }
}
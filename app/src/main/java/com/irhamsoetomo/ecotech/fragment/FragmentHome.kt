package com.irhamsoetomo.ecotech.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.irhamsoetomo.ecotech.EcotechApp
import com.irhamsoetomo.ecotech.activities.AccountInfoActivity
import com.irhamsoetomo.ecotech.databinding.FragmentHomeBinding
import com.irhamsoetomo.ecotech.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val authViewModel: AuthViewModel by viewModel()

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

        val sharedPref = EcotechApp.context.getSharedPreferences("ecoTechPref", Context.MODE_PRIVATE)
        val email = sharedPref.getString("emailShort", null)

        binding.tvGreetings.text = "Hello $email!"

        binding.settings.setOnClickListener {
            val intent = Intent(requireActivity(), AccountInfoActivity::class.java)
            startActivity(intent)
        }

    }
}
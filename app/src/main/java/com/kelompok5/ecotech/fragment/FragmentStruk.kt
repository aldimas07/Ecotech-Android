package com.kelompok5.ecotech.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kelompok5.ecotech.R
import com.kelompok5.ecotech.databinding.FragmentHomeBinding
import com.kelompok5.ecotech.databinding.FragmentStrukBinding

class FragmentStruk : Fragment() {

    private lateinit var binding: FragmentStrukBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStrukBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnTukarStruk.setOnClickListener{
            Toast.makeText(requireContext(), "Coming soon!", Toast.LENGTH_SHORT).show()
        }

    }

}
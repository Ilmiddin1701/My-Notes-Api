package com.ilmiddin1701.mynotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.mynotes.R
import com.ilmiddin1701.mynotes.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            btnDrawer.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }
            btnAdd.setOnClickListener { findNavController().navigate(R.id.addFragment) }
        }
        return binding.root
    }
}
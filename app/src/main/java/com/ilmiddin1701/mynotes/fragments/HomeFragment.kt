package com.ilmiddin1701.mynotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.mynotes.R
import com.ilmiddin1701.mynotes.databinding.FragmentHomeBinding
import com.ilmiddin1701.mynotes.utils.MySharedPreference

class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            MySharedPreference.init(requireContext())
            btnDrawer.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }
            btnAdd.setOnClickListener { findNavController().navigate(R.id.addFragment) }

            myNav.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_delete -> {

                    }

                    R.id.menu_logout -> {

                    }
                }
                true
            }
        }
        return binding.root
    }
}
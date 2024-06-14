package com.ilmiddin1701.mynotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.mynotes.R
import com.ilmiddin1701.mynotes.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private val binding by lazy { FragmentRegisterBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            btnSignIn.setOnClickListener {
                findNavController().navigate(R.id.homeFragment)
            }
        }
        return binding.root
    }
}
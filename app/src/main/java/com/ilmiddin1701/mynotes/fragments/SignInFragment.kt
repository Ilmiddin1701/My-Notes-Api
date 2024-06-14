package com.ilmiddin1701.mynotes.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.mynotes.R
import com.ilmiddin1701.mynotes.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private val binding by lazy { FragmentSignInBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            btnRegister.setOnClickListener {
                findNavController().navigate(R.id.registerFragment)
            }
            btnLogIn.setOnClickListener {
                findNavController().navigate(R.id.homeFragment)
            }
        }
        return binding.root
    }
}
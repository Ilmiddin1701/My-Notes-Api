package com.ilmiddin1701.mynotes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.mynotes.R
import com.ilmiddin1701.mynotes.databinding.FragmentSignInBinding
import com.ilmiddin1701.mynotes.utils.MySharedPreference

class SignInFragment : Fragment() {
    private val binding by lazy { FragmentSignInBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        MySharedPreference.init(binding.root.context)
        binding.apply {
            btnRegister.setOnClickListener {
                findNavController().navigate(R.id.registerFragment)
            }
            btnLogIn.setOnClickListener {

            }
        }
        return binding.root
    }
}
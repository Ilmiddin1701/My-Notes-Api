package com.ilmiddin1701.mynotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ilmiddin1701.mynotes.R
import com.ilmiddin1701.mynotes.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    private val binding by lazy { FragmentAboutBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {

        }
        return binding.root
    }
}
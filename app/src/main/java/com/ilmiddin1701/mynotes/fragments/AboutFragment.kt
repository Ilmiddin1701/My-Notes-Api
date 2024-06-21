package com.ilmiddin1701.mynotes.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.mynotes.R
import com.ilmiddin1701.mynotes.databinding.FragmentAboutBinding
import com.ilmiddin1701.mynotes.models.GetNoteResponse

@Suppress("DEPRECATION")
class AboutFragment : Fragment() {
    private val binding by lazy { FragmentAboutBinding.inflate(layoutInflater) }
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            val getNoteResponse = arguments?.getSerializable("keyInformation") as GetNoteResponse
            btnBack.setOnClickListener { findNavController().popBackStack() }
            btnEdit.setOnClickListener { findNavController().navigate(R.id.editFragment, bundleOf("keyInformation1" to getNoteResponse)) }
            tvTitle.text = getNoteResponse.sarlavha
            tvAbout.text = getNoteResponse.batafsil
            if (getNoteResponse.bajarildi) {
                tvBajarildi.text = "Bajarilgan "
                belgi.text = "✅"
            } else {
                tvBajarildi.text = "Bajarilmagan "
                belgi.text = "❌"
            }
        }
        return binding.root
    }
}
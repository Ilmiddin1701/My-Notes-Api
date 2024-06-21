package com.ilmiddin1701.mynotes.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ilmiddin1701.mynotes.R
import com.ilmiddin1701.mynotes.databinding.FragmentEditBinding
import com.ilmiddin1701.mynotes.models.GetNoteResponse

class EditFragment : Fragment() {
    private val binding by lazy { FragmentEditBinding.inflate(layoutInflater) }
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            val getNoteResponse = arguments?.getSerializable("keyInformation1") as GetNoteResponse
            edtSarlavha.setText(getNoteResponse.sarlavha)
            edtBatafsil.setText(getNoteResponse.batafsil)
            tvMuddat.text = getNoteResponse.muddat
            mySwitch.isChecked = getNoteResponse.bajarildi
        }
        return binding.root
    }
}
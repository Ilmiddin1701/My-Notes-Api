package com.ilmiddin1701.mynotes.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.mynotes.R
import com.ilmiddin1701.mynotes.databinding.FragmentEditBinding
import com.ilmiddin1701.mynotes.models.GetNoteResponse
import com.ilmiddin1701.mynotes.models.PutRequestNote
import com.ilmiddin1701.mynotes.retrofit.ApiClient
import com.ilmiddin1701.mynotes.utils.MySharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditFragment : Fragment() {
    private val binding by lazy { FragmentEditBinding.inflate(layoutInflater) }
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            MySharedPreference.init(requireContext())
            edtFocus.setOnClickListener { showKeyboard(edtBatafsil) }
            val itemId = arguments?.getInt("keyId1")
            ApiClient.getApiService().getResponseNotesId("Bearer "+MySharedPreference.token, itemId!!)
                .enqueue(object : Callback<GetNoteResponse> {
                    override fun onResponse(p0: Call<GetNoteResponse>, p1: Response<GetNoteResponse>) {
                        progressBar.visibility = View.GONE
                        if (p1.isSuccessful) {
                            edtSarlavha.setText(p1.body()?.sarlavha)
                            edtBatafsil.setText(p1.body()?.batafsil)
                            tvMuddat.text = p1.body()?.muddat
                            mySwitch.isChecked = p1.body()?.bajarildi!!
                            tvMuddat.setOnClickListener { showCalendar() }
                            btnCalendar.setOnClickListener { showCalendar() }
                            btnSave.setOnClickListener {
                                progressBar.visibility = View.VISIBLE
                                if (edtSarlavha.text.isNotEmpty() || edtBatafsil.text.isNotEmpty() || tvMuddat.text.isNotEmpty()) {
                                    val putRequestNote = PutRequestNote(
                                        mySwitch.isChecked,
                                        edtBatafsil.text.toString(),
                                        tvMuddat.text.toString(),
                                        edtSarlavha.text.toString()
                                    )
                                    ApiClient.getApiService().updateNote("Bearer ${MySharedPreference.token}", itemId, putRequestNote)
                                        .enqueue(object : Callback<String> {
                                            override fun onResponse(p0: Call<String>, p1: Response<String>) {
                                                if (p1.isSuccessful) {
                                                    progressBar.visibility = View.GONE
                                                    findNavController().popBackStack()
                                                } else {
                                                    val dialog = AlertDialog.Builder(requireContext()).create()
                                                    dialog.setTitle("Xatolik")
                                                    dialog.setMessage(p1.errorBody()?.string())
                                                    dialog.show()
                                                }
                                            }
                                            override fun onFailure(p0: Call<String>, p1: Throwable) {
                                                Toast.makeText(context, "Xatolik", Toast.LENGTH_SHORT).show()
                                            }
                                        })
                                } else {
                                    Toast.makeText(context, "Ma'lumotlar to'liq emas!", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            val dialog = AlertDialog.Builder(requireContext()).create()
                            dialog.setTitle("Xatolik")
                            dialog.setMessage(p1.errorBody()?.string())
                            dialog.show()
                        }
                    }
                    override fun onFailure(p0: Call<GetNoteResponse>, p1: Throwable) {
                        progressBar.visibility = View.GONE
                        Toast.makeText(context, "Xatolik", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        return binding.root
    }

    @SuppressLint("NewApi", "DefaultLocale", "SetTextI18n")
    private fun showCalendar() {
        binding.apply {
            val datePicker = DatePickerDialog(requireContext())
            datePicker.setOnDateSetListener { _, year, month, dayOfMonth ->
                tvMuddat.text = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            }
            datePicker.show()
        }
    }

    private fun showKeyboard(view: View) {
        view.requestFocus()
        val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}
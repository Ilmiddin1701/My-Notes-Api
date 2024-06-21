package com.ilmiddin1701.mynotes.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.mynotes.databinding.FragmentAddBinding
import com.ilmiddin1701.mynotes.models.PostRequestNote
import com.ilmiddin1701.mynotes.retrofit.ApiClient
import com.ilmiddin1701.mynotes.utils.MySharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFragment : Fragment() {
    private val binding by lazy { FragmentAddBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        MySharedPreference.init(binding.root.context)
        binding.apply {
            edtFocus.setOnClickListener { showKeyboard(edtBatafsil) }

            tvMuddat.setOnClickListener { showCalendar() }
            btnCalendar.setOnClickListener { showCalendar() }

            btnSave.setOnClickListener {
                if (edtSarlavha.text.isNotEmpty() && edtBatafsil.text.isNotEmpty() && tvMuddat.text.isNotEmpty()) {
                    val postRequestNote = PostRequestNote(
                        edtSarlavha.text.toString(),
                        edtBatafsil.text.toString(),
                        tvMuddat.text.toString()
                    )
                    ApiClient.getApiService()
                        .postRequestNote("Bearer ${MySharedPreference.token}", postRequestNote)
                        .enqueue(object : Callback<String> {
                            override fun onResponse(p0: Call<String>, p1: Response<String>) {
                                if (p1.isSuccessful) {
                                    findNavController().popBackStack()
                                    Toast.makeText(context, "Saqlandi", Toast.LENGTH_SHORT).show()
                                } else {
                                    val dialog = AlertDialog.Builder(requireContext())
                                    dialog.setTitle("Xatolik")
                                    dialog.setMessage(p1.errorBody()?.string())
                                    dialog.show()
                                }
                            }

                            override fun onFailure(p0: Call<String>, p1: Throwable) {
                                Toast.makeText(context, "Xatolik" + p1.message, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        })
                } else {
                    Toast.makeText(context, "Ma'lumotlar to'liq emas!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding.root
    }

    private fun showKeyboard(view: View) {
        view.requestFocus()
        val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    private fun showCalendar() {
        binding.apply {
            val datePicker = DatePickerDialog(requireContext())
            datePicker.setOnCancelListener {
                tvMuddat.text = "Muddat"
            }
            datePicker.setOnDateSetListener { _, year, month, dayOfMonth ->
                tvMuddat.text = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            }
            datePicker.show()
        }
    }
}
package com.ilmiddin1701.mynotes.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.mynotes.R
import com.ilmiddin1701.mynotes.databinding.FragmentAboutBinding
import com.ilmiddin1701.mynotes.models.GetNoteResponse
import com.ilmiddin1701.mynotes.retrofit.ApiClient
import com.ilmiddin1701.mynotes.utils.MySharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class AboutFragment : Fragment() {
    private val binding by lazy { FragmentAboutBinding.inflate(layoutInflater) }
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            MySharedPreference.init(requireContext())
            btnBack.setOnClickListener { findNavController().popBackStack() }
            val itemId = arguments?.getInt("keyId")
            ApiClient.getApiService().getResponseNotesId("Bearer "+MySharedPreference.token, itemId!!)
                .enqueue(object : Callback<GetNoteResponse> {
                    override fun onResponse(p0: Call<GetNoteResponse>, p1: Response<GetNoteResponse>) {
                        progressBar.visibility = View.GONE
                        if (p1.isSuccessful) {
                            tvTitle.text = p1.body()?.sarlavha
                            tvAbout.text = p1.body()?.batafsil
                            if (p1.body()?.bajarildi!!) {
                                tvBajarildi.text = "Bajarilgan "
                                belgi.text = "✅"
                            } else {
                                tvBajarildi.text = "Bajarilmagan "
                                belgi.text = "❌"
                            }
                            btnEdit.setOnClickListener {
                                findNavController().navigate(R.id.editFragment, bundleOf("keyId1" to itemId))
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
}
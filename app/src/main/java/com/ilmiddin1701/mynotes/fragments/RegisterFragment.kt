package com.ilmiddin1701.mynotes.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ilmiddin1701.mynotes.databinding.FragmentRegisterBinding
import com.ilmiddin1701.mynotes.models.PostRequestUser
import com.ilmiddin1701.mynotes.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterFragment : Fragment() {
    private val binding by lazy { FragmentRegisterBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            btnSignUp.setOnClickListener {
                if (edtPassword.text.isNotEmpty() && edtUsername.text.isNotEmpty()) {
                    progressBar.visibility = View.VISIBLE
                    btnSignUp.isEnabled = false
                    val postRequestUser = PostRequestUser (
                        username = edtUsername.text.toString(),
                        password = edtPassword.text.toString()
                    )
                    ApiClient.getApiService().registerUser(postRequestUser)
                        .enqueue(object : Callback<String>{
                            override fun onResponse(p0: Call<String>, p1: Response<String>) {
                                progressBar.visibility = View.GONE
                                btnSignUp.isEnabled = true
                                if (p1.isSuccessful){
                                    Toast.makeText(context, p1.body(), Toast.LENGTH_SHORT).show()
                                } else {
                                    val dialog = AlertDialog.Builder(context)
                                    dialog.setTitle("Xatolik")
                                    dialog.setMessage(p1.errorBody()?.string())
                                    dialog.show()
                                }
                            }
                            override fun onFailure(p0: Call<String>, p1: Throwable) {
                                Toast.makeText(context, "Error" + p1.message, Toast.LENGTH_SHORT).show()
                                progressBar.visibility = View.GONE
                                btnSignUp.isEnabled = true
                            }
                        })
                } else {
                    Toast.makeText(context, "Ma'lumotlar to'liq emas", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding.root
    }
}
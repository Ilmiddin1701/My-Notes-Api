package com.ilmiddin1701.mynotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

            btnSignIn.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                btnSignIn.isEnabled = false
                val postRequestUser = PostRequestUser(
                    edtPassword.text.toString(),
                    edtUsername.text.toString()
                )
                ApiClient.getApiService().registerUser(postRequestUser)
                    .enqueue(object : Callback<String> {
                        override fun onResponse(p0: Call<String>, p1: Response<String>) {
                            progressBar.visibility = View.GONE
                            btnSignIn.isEnabled = true
                            if (p1.isSuccessful){
                                Toast.makeText(context, "${p1.body()}", Toast.LENGTH_SHORT).show()
                            }else{
                                val dialog = AlertDialog.Builder(binding.root.context)
                                dialog.setTitle("Xatolik")
                                dialog.setMessage("${p1.errorBody()?.string()}")
                                dialog.show()
                            }
                        }

                        override fun onFailure(p0: Call<String>, p1: Throwable) {
                            Toast.makeText(context, "Error ${p1.message}", Toast.LENGTH_SHORT)
                                .show()
                            progressBar.visibility = View.GONE
                            btnSignIn.isEnabled = true
                        }
                    })
            }

        }
        return binding.root
    }
}
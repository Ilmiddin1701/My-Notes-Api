package com.ilmiddin1701.mynotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.mynotes.R
import com.ilmiddin1701.mynotes.databinding.FragmentRegisterBinding
import com.ilmiddin1701.mynotes.models.PostRequestUser
import com.ilmiddin1701.mynotes.models.PostResponseToken
import com.ilmiddin1701.mynotes.retrofit.ApiClient
import com.ilmiddin1701.mynotes.utils.MySharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterFragment : Fragment() {
    private val binding by lazy { FragmentRegisterBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        MySharedPreference.init(binding.root.context)
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallBack)
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
                                if (p1.isSuccessful) {
                                    ApiClient.getApiService().signInUser(postRequestUser)
                                        .enqueue(object : Callback<PostResponseToken>{
                                            override fun onResponse(p0: Call<PostResponseToken>, p1: Response<PostResponseToken>) {
                                                progressBar.visibility = View.GONE
                                                btnSignUp.isEnabled = true
                                                if (p1.isSuccessful) {
                                                    val accessToken = p1.body()?.access
                                                    MySharedPreference.token = accessToken!!
                                                    findNavController().popBackStack()
                                                    findNavController().navigate(R.id.homeFragment)
                                                    Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show()
                                                } else {
                                                    val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
                                                    dialog.setTitle("Error")
                                                    dialog.setMessage(p1.errorBody()?.string())
                                                    dialog.show()
                                                }
                                            }
                                            override fun onFailure(p0: Call<PostResponseToken>, p1: Throwable) {
                                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                                                progressBar.visibility = View.GONE
                                                btnSignUp.isEnabled = true
                                            }
                                        })
                                } else {
                                    progressBar.visibility = View.GONE
                                    btnSignUp.isEnabled = true
                                    val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
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

    private val onBackPressedCallBack = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            findNavController().navigate(R.id.signInFragment)
        }
    }
}
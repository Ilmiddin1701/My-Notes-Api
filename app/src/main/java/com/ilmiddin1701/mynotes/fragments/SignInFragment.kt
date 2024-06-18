package com.ilmiddin1701.mynotes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.mynotes.R
import com.ilmiddin1701.mynotes.databinding.FragmentSignInBinding
import com.ilmiddin1701.mynotes.models.PostRequestUser
import com.ilmiddin1701.mynotes.models.PostResponseToken
import com.ilmiddin1701.mynotes.retrofit.ApiClient
import com.ilmiddin1701.mynotes.utils.MySharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInFragment : Fragment() {
    private val binding by lazy { FragmentSignInBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        MySharedPreference.init(binding.root.context)
        if (MySharedPreference.token != "null") {
            findNavController().popBackStack()
            findNavController().navigate(R.id.homeFragment)
        }
        binding.apply {
            btnRegister.setOnClickListener {
                findNavController().navigate(R.id.registerFragment)
            }
            btnSignIn.setOnClickListener {
                if(edtPassword.text.isNotEmpty() && edtUsername.text.isNotEmpty()) {
                    progressBar.visibility = View.VISIBLE
                    btnSignIn.isEnabled = false
                    val postRequestUser = PostRequestUser(
                        edtPassword.text.toString(),
                        edtUsername.text.toString()
                    )
                    ApiClient.getApiService().signInUser(postRequestUser)
                        .enqueue(object : Callback<PostResponseToken>{
                            override fun onResponse(p0: Call<PostResponseToken>, p1: Response<PostResponseToken>) {
                                progressBar.visibility = View.GONE
                                btnSignIn.isEnabled = true
                                if (p1.isSuccessful) {
                                    val accessToken = p1.body()?.access
                                    MySharedPreference.token = accessToken!!
                                    findNavController().popBackStack()
                                    findNavController().navigate(R.id.homeFragment)
                                    Toast.makeText(context, p1.message(), Toast.LENGTH_SHORT).show()
                                } else {
                                    val dialog = AlertDialog.Builder(requireContext())
                                    dialog.setTitle("Xatolik")
                                    dialog.setMessage(p1.errorBody()?.string())
                                    dialog.show()
                                }
                            }
                            override fun onFailure(p0: Call<PostResponseToken>, p1: Throwable) {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                                progressBar.visibility = View.GONE
                                btnSignIn.isEnabled = true
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
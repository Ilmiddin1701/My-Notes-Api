package com.ilmiddin1701.mynotes.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        if (MySharedPreference.token!="null"){
            findNavController().popBackStack()
            findNavController().navigate(R.id.homeFragment)
            return binding.root
        }

        binding.apply {
            btnRegister.setOnClickListener {
                findNavController().navigate(R.id.registerFragment)
            }
            btnLogIn.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                btnLogIn.isEnabled = false
                val postRequestUser = PostRequestUser(
                    edtPassword.text.toString(),
                    edtUsername.text.toString()
                )
                ApiClient.getApiService().getToken(postRequestUser)
                    .enqueue(object :Callback<PostResponseToken>{
                        override fun onResponse(
                            p0: Call<PostResponseToken>,
                            p1: Response<PostResponseToken>
                        ) {
                            progressBar.visibility = View.GONE
                            btnLogIn.isEnabled = true
                            if (p1.isSuccessful){
                                val t = p1.body()?.access
                                MySharedPreference.token = t!!
                                Toast.makeText(context, "${p1.message()}", Toast.LENGTH_SHORT)
                                    .show()
                                findNavController().popBackStack()
                                findNavController().navigate(R.id.homeFragment)
                            }else{
                                val dialog = AlertDialog.Builder(binding.root.context)
                                dialog.setTitle("Xatolik")
                                dialog.setMessage("${p1.errorBody()?.string()}")
                                dialog.show()
                            }
                        }

                        override fun onFailure(p0: Call<PostResponseToken>, p1: Throwable) {
                            progressBar.visibility = View.GONE
                            btnLogIn.isEnabled = true
                            Toast.makeText(context, "Error ${p1.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                    })
            }
        }
        return binding.root
    }
}
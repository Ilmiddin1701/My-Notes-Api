package com.ilmiddin1701.mynotes.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.mynotes.R
import com.ilmiddin1701.mynotes.databinding.FragmentHomeBinding
import com.ilmiddin1701.mynotes.models.GetUserResponse
import com.ilmiddin1701.mynotes.retrofit.ApiClient
import com.ilmiddin1701.mynotes.utils.MySharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "HomeFragment"
class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            MySharedPreference.init(requireContext())
            ApiClient.getApiService().getUserDetails("Bearer "+MySharedPreference.token)
                .enqueue(object : Callback<GetUserResponse>{
                    override fun onResponse(
                        p0: Call<GetUserResponse>,
                        p1: Response<GetUserResponse>
                    ) {
                        if (p1.isSuccessful){
                            val user = p1.body()
                            Log.d(TAG, "onResponse: $user")
                            myNav.findViewById<TextView>(R.id.tvUsername)
                                .text = user?.username
                        }else{
                            val dialog = AlertDialog.Builder(binding.root.context)
                            dialog.setTitle("Xatolik")
                            dialog.setMessage("${p1.errorBody()?.string()}")
                            dialog.show()
                        }
                    }

                    override fun onFailure(p0: Call<GetUserResponse>, p1: Throwable) {
                        Toast.makeText(context, "Error ${p1.message}", Toast.LENGTH_SHORT).show()
                    }
                })


            btnDrawer.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }
            btnAdd.setOnClickListener { findNavController().navigate(R.id.addFragment) }

            myNav.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.menu_delete->{
                        ApiClient.getApiService().deleteUser("Bearer ${MySharedPreference.token}")
                            .enqueue(object : Callback<Any>{
                                override fun onResponse(p0: Call<Any>, p1: Response<Any>) {
                                    if (p1.isSuccessful){
                                        MySharedPreference.token = "null"
                                        requireActivity().finish()
                                    }else{
                                        val dialog = AlertDialog.Builder(root.context)
                                        dialog.setTitle("Xatolik")
                                        dialog.setMessage("${p1.errorBody()?.string()}")
                                        dialog.show()
                                    }
                                }

                                override fun onFailure(p0: Call<Any>, p1: Throwable) {
                                    Toast.makeText(context, "Error ${p1.message}", Toast.LENGTH_SHORT).show()
                                }
                            })
                    }
                }
                true
            }
        }
        return binding.root
    }
}
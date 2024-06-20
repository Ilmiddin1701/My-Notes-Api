package com.ilmiddin1701.mynotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.mynotes.R
import com.ilmiddin1701.mynotes.adapters.RvAdapter
import com.ilmiddin1701.mynotes.databinding.FragmentHomeBinding
import com.ilmiddin1701.mynotes.models.GetNoteResponse
import com.ilmiddin1701.mynotes.models.GetResponseUser
import com.ilmiddin1701.mynotes.retrofit.ApiClient
import com.ilmiddin1701.mynotes.utils.MySharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(), RvAdapter.RvAction {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var rvAdapter: RvAdapter
    private lateinit var mySharedPreference: MySharedPreference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            mySharedPreference = MySharedPreference
            mySharedPreference.init(requireContext())
            if (MySharedPreference.token == "empty") {
                findNavController().popBackStack()
                findNavController().navigate(R.id.signInFragment)
                return binding.root
            }
            tvTitle.text = "Connecting..."
            ApiClient.getApiService().getUserDetails("Bearer ${MySharedPreference.token}")
                .enqueue(object : Callback<GetResponseUser> {
                    override fun onResponse(
                        p0: Call<GetResponseUser>,
                        p1: Response<GetResponseUser>
                    ) {
                        if (p1.isSuccessful) {
                            val user = p1.body()
                            myNav.findViewById<TextView>(R.id.tvUsername)
                                .text = user?.username
                            onResume()
                        }
                    }

                    override fun onFailure(p0: Call<GetResponseUser>, p1: Throwable) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }
                })
            btnDrawer.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }
            btnAdd.setOnClickListener { findNavController().navigate(R.id.addFragment) }

            myNav.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_delete -> {
                        ApiClient.getApiService().deleteUser("Bearer ${MySharedPreference.token}")
                            .enqueue(object : Callback<Any> {
                                override fun onResponse(p0: Call<Any>, p1: Response<Any>) {}
                                override fun onFailure(p0: Call<Any>, p1: Throwable) {
                                    mySharedPreference.token = "empty"
                                    MySharedPreference.token = mySharedPreference.token
                                    findNavController().popBackStack()
                                    findNavController().navigate(R.id.signInFragment)
                                }
                            })
                    }

                    R.id.menu_logout -> {
                        mySharedPreference.token = "empty"
                        MySharedPreference.token = mySharedPreference.token
                        findNavController().popBackStack()
                        findNavController().navigate(R.id.signInFragment)
                    }
                }
                true
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        ApiClient.getApiService().getResponseNotes("Bearer ${MySharedPreference.token}")
            .enqueue(object : Callback<List<GetNoteResponse>> {
                override fun onResponse(
                    p0: Call<List<GetNoteResponse>>,
                    p1: Response<List<GetNoteResponse>>
                ) {
                    if (p1.isSuccessful) {
                        binding.apply {
                            tvTitle.text = "Todo App"
                            rvAdapter = RvAdapter(
                                this@HomeFragment,
                                p1.body() as ArrayList<GetNoteResponse>
                            )
                            rv.adapter = rvAdapter
                        }
                    }
                }

                override fun onFailure(p0: Call<List<GetNoteResponse>>, p1: Throwable) {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun moreClick(getNoteResponse: GetNoteResponse, position: Int, imageView: ImageView) {

    }

    override fun itemClick(getNoteResponse: GetNoteResponse, position: Int) {

    }
}
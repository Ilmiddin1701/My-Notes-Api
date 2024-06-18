package com.ilmiddin1701.mynotes.retrofit

import com.ilmiddin1701.mynotes.models.PostRequestUser
import com.ilmiddin1701.mynotes.models.PostResponseToken
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("user/register/")
    fun registerUser(@Body postRequestUser: PostRequestUser): Call<String>

    @POST("user/token/")
    fun signInUser(@Body postRequestUser: PostRequestUser): Call<PostResponseToken>
}
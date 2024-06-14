package com.ilmiddin1701.mynotes.retrofit

import com.ilmiddin1701.mynotes.models.GetUserResponse
import com.ilmiddin1701.mynotes.models.PostRequestUser
import com.ilmiddin1701.mynotes.models.PostResponseToken
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("user/register/")
    fun registerUser(@Body postRequestUser: PostRequestUser):Call<String>

    @POST("user/token/")
    fun getToken(@Body postRequestUser: PostRequestUser):Call<PostResponseToken>

    @GET("user/details/")
    fun getUserDetails(
        @Header("Authorization") token:String
    ):Call<GetUserResponse>

    @DELETE("user/delete/")
    fun deleteUser(@Header("Authorization") token: String):Call<Any>
}
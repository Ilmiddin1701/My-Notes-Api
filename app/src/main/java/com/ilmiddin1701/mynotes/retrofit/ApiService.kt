package com.ilmiddin1701.mynotes.retrofit

import com.ilmiddin1701.mynotes.models.GetNoteResponse
import com.ilmiddin1701.mynotes.models.GetResponseUser
import com.ilmiddin1701.mynotes.models.PostRequestNote
import com.ilmiddin1701.mynotes.models.PostRequestUser
import com.ilmiddin1701.mynotes.models.PostResponseToken
import com.ilmiddin1701.mynotes.models.PutRequestNote
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @POST("user/register/")
    fun registerUser(@Body postRequestUser: PostRequestUser): Call<String>

    @POST("user/token/")
    fun signInUser(@Body postRequestUser: PostRequestUser): Call<PostResponseToken>

    @GET("user/details/")
    fun getUserDetails(@Header("Authorization") token: String): Call<GetResponseUser>

    @DELETE("user/delete/")
    fun deleteUser(@Header("Authorization") token: String): Call<Any>


    @POST("main/reja-qo'shish/")
    fun postRequestNote(@Header("Authorization") token: String, @Body postRequestNote: PostRequestNote): Call<String>

    @GET("main/rejalar/")
    fun getResponseNotes(@Header("Authorization") token: String): Call<List<GetNoteResponse>>

    @GET("/main/rejalar/{id}/")
    fun getResponseNotesId(@Header("Authorization") token: String, @Path("id") id: Int): Call<GetNoteResponse>

    @DELETE("main/rejalar/{id}/o'chirish/")
    fun deleteNote(@Header("Authorization") token: String, @Path("id") id: Int): Call<Any>

    @PUT("/main/rejalar/{reja_id}/tahrirlash/")
    fun updateNote(@Header("Authorization") token: String, @Path("reja_id") id: Int, @Body putRequestNote: PutRequestNote): Call<String>
}
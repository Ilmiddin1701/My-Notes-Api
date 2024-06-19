package com.ilmiddin1701.mynotes.models

data class GetResponseUser(
    val date_joined: String,
    val email: String,
    val familiya: Any,
    val id: Int,
    val ism: Any,
    val password: String,
    val username: String
)
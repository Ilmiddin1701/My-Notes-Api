package com.ilmiddin1701.mynotes.models

data class PostRequestNote(
    val content: String,
    val title: String,
    val user: Int
)
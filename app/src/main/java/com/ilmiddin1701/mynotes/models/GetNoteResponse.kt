package com.ilmiddin1701.mynotes.models

data class GetNoteResponse(
    val content: String,
    val created_at: String,
    val id: Int,
    val title: String,
    val updated_at: String,
    val user: Int
)
package com.ilmiddin1701.mynotes.models

import java.io.Serializable

data class GetNoteResponse(
    val bajarildi: Boolean,
    val batafsil: String,
    val created_at: String,
    val id: Int,
    val muddat: String,
    val sarlavha: String,
    val user: Int
): Serializable
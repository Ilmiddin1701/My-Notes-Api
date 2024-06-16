package com.ilmiddin1701.mynotes.utils

import android.content.Context
import android.content.SharedPreferences

object MySharedPreference {
    private const val NAME = "catch_file_name"
    private const val MODE = Context.MODE_PRIVATE

    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var token: String
        get() = preferences.getString("keyToken", "null")!!
        set(value) = preferences.edit {
            if (value != null) {
                it.putString("keyToken", value)
            }
        }
}
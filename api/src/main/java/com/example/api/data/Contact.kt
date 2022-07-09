package com.example.api.data

import android.graphics.Bitmap

data class Contact(
    val id: Long,
    val name: String,
    val telephone: List<String>,
    val photo: Bitmap?
)

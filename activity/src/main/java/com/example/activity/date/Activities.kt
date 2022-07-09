package com.example.activity.date

import java.util.*

data class Activities(
   val name: String,
   val type: String,
   val date: Calendar?,
   val time: Int,
   val distance: Float,
   val description: String
)

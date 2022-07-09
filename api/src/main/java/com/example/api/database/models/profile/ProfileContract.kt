package com.example.api.database.models.profile

object ProfileContract {
    const val TABLE_NAME = "profile"

    object Columns {
        const val ID = "id"
        const val FIRSTNAME = "firstname"
        const val LASTNAME = "lastname"
        const val COUNTRY = "country"
        const val SEX = "sex"
        const val WEIGHT = "weight"
        const val PROFILE = "profile"
        const val FOLLOWERCOUNT = "follower_Count"
        const val FRIENDCOUNT = "friend_Count"
    }
}

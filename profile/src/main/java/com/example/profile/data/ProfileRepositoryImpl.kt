package com.example.profile.data

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.ContactsContract
import android.util.Log
import com.example.api.data.ApiAthlete
import com.example.api.data.ApiToken
import com.example.api.data.Athlete
import com.example.api.data.Contact
import com.example.api.database.models.profile.ProfileDao
import com.example.stravarun.database.models.profile.Profile
import com.example.utils.ObjectApp
import com.example.utils.ObjectApp.ACCESS_TOKEN
import com.example.utils.ObjectApp.EXPIRES_AT_TOKEN
import com.example.utils.ObjectApp.EXPIRES_IN_TOKEN
import com.example.utils.ObjectApp.LOCAL_DATE
import com.example.utils.ObjectApp.REFRESH_TOKEN
import com.example.utils.ObjectApp.TOKEN_TYPE
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apiAthlete: ApiAthlete,
    private val apiToken: ApiToken,
    private val profileDao: ProfileDao,
    private val contentResolver: ContentResolver,
    @ApplicationContext context: Context
) : ProfileRepository {

    private val sharedPreferences = context.getSharedPreferences(
        ObjectApp.SHARED_PREFS_NAME,
        Context.MODE_PRIVATE)

    override suspend fun getProfileAthlete(): Athlete {
        return try {
            val athlete = apiAthlete.getProfileAthlete()
            insertProfileDB(athlete)
            athlete
        } catch (t: Throwable) {
            Log.d("stravaError", "ProfileRepository ${t.message.toString()}")
            getProfileDB()
        }
    }

    override suspend fun logout() {
        val accessToken = sharedPreferences.getString(ACCESS_TOKEN, null)
        if (accessToken != null) {
            apiToken.logout(accessToken)
            sharedPreferences.edit().remove(ACCESS_TOKEN).apply()
            sharedPreferences.edit().remove(TOKEN_TYPE).apply()
            sharedPreferences.edit().remove(EXPIRES_AT_TOKEN).apply()
            sharedPreferences.edit().remove(EXPIRES_IN_TOKEN).apply()
            sharedPreferences.edit().remove(REFRESH_TOKEN).apply()
        }
    }

    override suspend fun updateWeight(weight: Int) {
        apiAthlete.updateWeight(weight.toFloat())
    }

    override suspend fun getContact(): List<Contact> = withContext(Dispatchers.IO) {
        contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )?.use {
            getContactsFromCursor(it)
        }.orEmpty()
    }

    override suspend fun getIdAthlete(): String {
        return getProfileDB().id
    }

    private fun getContactsFromCursor(cursor: Cursor): List<Contact> {
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<Contact>()
        do {
            val idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
            val id = cursor.getLong(idIndex)
            val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
            val name = cursor.getString(nameIndex).orEmpty()
            val phone = phonesNumbers(id)
            val photo = openPhoto(id)
            list.add(Contact(id, name, phone, photo))
        } while (cursor.moveToNext())
        return list
    }

    private fun phonesNumbers(id: Long): List<String> {
        return contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID + " = " + id,
            null,
            null
        )?.use {
            getPhoneNumbers(it)
        }.orEmpty()
    }

    private fun getPhoneNumbers(cursor: Cursor): List<String> {
        if (cursor.moveToFirst().not()) return emptyList()
        val listPhone = mutableListOf<String>()
        do {
            val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val number = cursor.getString(numberIndex)
            listPhone.add(number)
        } while (cursor.moveToNext())
        return listPhone
    }

    private fun openPhoto(contactId: Long): Bitmap? {
        val contactUri =
            ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId)
        val photo = ContactsContract.Contacts.openContactPhotoInputStream(
            contentResolver,
            contactUri
        )
        return BitmapFactory.decodeStream(photo)
    }

    private suspend fun getProfileDB(): Athlete {
        val athleteDB = profileDao.getProfile()
        return Athlete(
            athleteDB.id,
            athleteDB.firstName,
            athleteDB.lastName,
            athleteDB.country,
            athleteDB.sex,
            athleteDB.weight,
            athleteDB.profile,
            athleteDB.followerCount,
            athleteDB.friendCount
        )
    }

    private suspend fun insertProfileDB(athlete: Athlete) {
        profileDao.insertProfile(
            Profile(
                athlete.id,
                athlete.firstname,
                athlete.lastname,
                athlete.country,
                athlete.sex,
                athlete.weight,
                athlete.profile,
                athlete.followerCount,
                athlete.friendCount
            )
        )
    }

    override fun isPushNotif(): Boolean {
        val localDate = System.currentTimeMillis()
        val localDateUse = sharedPreferences.getLong(LOCAL_DATE, 0)
        return (localDate - localDateUse) >= 86400 && localDateUse != 0L
    }
}
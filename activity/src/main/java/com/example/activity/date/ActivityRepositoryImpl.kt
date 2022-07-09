package com.example.activity.date


import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.work.*
import com.example.api.data.ApiAthlete
import com.example.api.data.ListActivities
import com.example.api.data.ListActivitiesWithProfile
import com.example.api.database.models.list_activities.ListActivitiesDB
import com.example.api.database.models.list_activities.ListActivitiesDaoDB
import com.example.api.database.models.profile.ProfileDao
import com.example.utils.ObjectApp.DOWNLOAD_WORK_ID
import com.example.utils.ObjectApp.LOCAL_DATE
import com.example.utils.ObjectApp.SHARED_PREFS_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ActivityRepositoryImpl @Inject constructor(
    private val apiAthlete: ApiAthlete,
    private val profileDao: ProfileDao,
    private val activitiesDao: ListActivitiesDaoDB,
   @ApplicationContext private val context: Context
) : ActivityRepository {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME,Context.MODE_PRIVATE)

    override suspend fun listActivity(callbackIsCache: (Boolean) -> Unit): List<ListActivitiesWithProfile> {
        return try {
            val listActivities = apiAthlete.getListActivities()
            callbackIsCache(false)
            insertListActivitiesDB(listActivities)
            createListActivityWithProfile(listActivities)
        } catch (t: Throwable) {
            callbackIsCache(true)
            createListActivityWithProfile(getListActivitiesDB())
        }
    }

    private suspend fun createListActivityWithProfile(listActivities: List<ListActivities>): List<ListActivitiesWithProfile> {
        val list = mutableListOf<ListActivitiesWithProfile>()
        val profile = profileDao.getProfile()
        listActivities.forEach {
            val activitiesWithProfile = ListActivitiesWithProfile(
                it.name,
                it.distance,
                it.time,
                it.startDate,
                it.type,
                it.elevationGain,
                profile.firstName,
                profile.lastName,
                profile.profile
            )
            list.add(activitiesWithProfile)
        }
        return list
    }

    private suspend fun insertListActivitiesDB(listActivities: List<ListActivities>) {
        val listActivitiesDB = mutableListOf<ListActivitiesDB>()
        listActivities.forEach {

            val activities = ListActivitiesDB(
                it.name,
                it.distance,
                it.time,
                it.startDate,
                it.type,
                it.elevationGain,
                "",
                true
            )
            listActivitiesDB.add(activities)
        }
        activitiesDao.insertListActivities(listActivitiesDB)
    }

    private suspend fun getListActivitiesDB(): List<ListActivities> {
        return try {
            val listActivities = mutableListOf<ListActivities>()
            val listDB = activitiesDao.getListActivities()
            listDB.forEach {
                val activities = ListActivities(
                    it.name,
                    it.distance,
                    it.time,
                    it.startDate,
                    it.type,
                    it.elevationGain
                )
                listActivities.add(activities)
            }
            listActivities
        } catch (t: Throwable) {
            emptyList()
        }
    }

    override suspend fun addActivities(activities: Activities) {

        val formatDate = activities.date?.time?.let {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(
                it
            )
        }
        val localDate = System.currentTimeMillis()
        sharedPreferences.edit().putLong(LOCAL_DATE,localDate).apply()

        try {
            apiAthlete.createActivity(
                activities.name,
                activities.type,
                formatDate.toString(),
                activities.time,
                activities.description,
                activities.distance,
                0,
                0
            )
        } catch (t: Throwable) {

            val activitiesDB = ListActivitiesDB(
                activities.name,
                activities.distance.toString(),
                activities.time.toString(),
                formatDate.toString(),
                activities.type,
                "0",
                activities.description,
                false
            )
            Log.d("StravaError","save DataBase")
            activitiesDao.insertListActivities(listOf(activitiesDB))
            workManager()
        }
    }

    private fun workManager() {
        val workConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.METERED)
            .build()
        val workRequest = OneTimeWorkRequestBuilder<PushWorker>()
            .setConstraints(workConstraints)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 30, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(context)
            .enqueueUniqueWork(DOWNLOAD_WORK_ID, ExistingWorkPolicy.KEEP, workRequest)
    }
}

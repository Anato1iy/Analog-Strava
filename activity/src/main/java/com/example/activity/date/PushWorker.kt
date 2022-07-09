package com.example.activity.date

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.example.api.data.ApiAthlete
import com.example.api.database.models.list_activities.ListActivitiesDaoDB
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltWorker
class PushWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val activitiesDao: ListActivitiesDaoDB,
    private val apiAthlete: ApiAthlete
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        Log.d("asd","start save activ 1")
        val activitiesList = activitiesDao.getListActivitiesNoSynch()
        activitiesList.forEach { activities ->

            Log.d("asd","start save activ 2")
            val formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val dateActivities = ZonedDateTime.parse(activities.startDate)
                .minusHours(8)
                .format(formatDate)

            apiAthlete.createActivity(
                activities.name,
                activities.type,
                dateActivities,
                activities.time.toInt(),
                activities.description,
                activities.distance.toFloat(),
                0,
                0
            )
        }

        return Result.success()
    }
}
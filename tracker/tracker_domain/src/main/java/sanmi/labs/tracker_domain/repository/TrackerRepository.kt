package sanmi.labs.tracker_domain.repository

import kotlinx.coroutines.flow.Flow
import sanmi.labs.tracker_domain.model.TrackableFood
import sanmi.labs.tracker_domain.model.TrackedFood
import java.time.LocalDate

interface TrackerRepository {

    suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int,
    ): Result<List<TrackableFood>>

    suspend fun insertTrackedFood(trackedFood: TrackedFood)

    suspend fun  deleteTrackedFood(trackedFood: TrackedFood)

    fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>>
}
package sanmi.labs.tracker_data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import sanmi.labs.tracker_data.local.TrackerDao
import sanmi.labs.tracker_data.mapper.toTrackableFood
import sanmi.labs.tracker_data.mapper.toTrackedFood
import sanmi.labs.tracker_data.mapper.toTrackedFoodEntity
import sanmi.labs.tracker_data.remote.OpenFoodApi
import sanmi.labs.tracker_domain.model.TrackableFood
import sanmi.labs.tracker_domain.model.TrackedFood
import sanmi.labs.tracker_domain.repository.TrackerRepository
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val dao: TrackerDao,
    private val api: OpenFoodApi,
): TrackerRepository {

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return try {
            val searchDto = api.searchFood(query, page, pageSize)
            Result.success(
                searchDto.products.mapNotNull { it.toTrackableFood() }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertTrackedFood(trackedFood: TrackedFood) {
        dao.insertTrackedFood(trackedFood.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(trackedFood: TrackedFood) {
        dao.deleteTrackedFood(trackedFood.toTrackedFoodEntity())
    }

    override suspend fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return dao.getFoodsByDate(
            day = localDate.dayOfMonth,
            month = localDate.monthValue,
            year = localDate.year
        ).map { entities ->
            entities.map { it.toTrackedFood() }
        }
    }
}
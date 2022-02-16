package sanmi.labs.tracker_data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import sanmi.labs.tracker_data.local.entity.TrackedFoodEntity

@Dao
interface TrackerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackedFood(trackedFoodEntity: TrackedFoodEntity)

    @Delete
    suspend fun deleteTrackedFood(trackedFoodEntity: TrackedFoodEntity)

    @Query(
        """
            SELECT *
            FROM trackedfoodentity
            WHERE dayOfMonth = :day AND month = :month AND year = :year
        """
    )
    fun getFoodsByDate(day: Int, month: Int, year: Int): Flow<List<TrackedFoodEntity>>
}
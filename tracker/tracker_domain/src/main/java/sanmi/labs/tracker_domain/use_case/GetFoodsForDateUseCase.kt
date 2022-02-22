package sanmi.labs.tracker_domain.use_case

import kotlinx.coroutines.flow.Flow
import sanmi.labs.tracker_domain.model.TrackedFood
import sanmi.labs.tracker_domain.repository.TrackerRepository
import java.time.LocalDate

class GetFoodsForDateUseCase(
    private val repository: TrackerRepository
) {
    operator fun invoke(
        date: LocalDate
    ): Flow<List<TrackedFood>> {
        return repository.getFoodsForDate(date)
    }
}
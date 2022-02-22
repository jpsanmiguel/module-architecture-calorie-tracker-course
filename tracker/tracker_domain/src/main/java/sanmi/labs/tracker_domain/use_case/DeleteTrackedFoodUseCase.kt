package sanmi.labs.tracker_domain.use_case

import sanmi.labs.tracker_domain.model.TrackedFood
import sanmi.labs.tracker_domain.repository.TrackerRepository

class DeleteTrackedFoodUseCase(
    private val repository: TrackerRepository
) {
    suspend operator fun invoke(
        trackedFood: TrackedFood
    ) {
        repository.deleteTrackedFood(trackedFood)
    }
}
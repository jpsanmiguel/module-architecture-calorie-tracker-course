package sanmi.labs.tracker_domain.use_case

import sanmi.labs.tracker_domain.model.MealType
import sanmi.labs.tracker_domain.model.TrackableFood
import sanmi.labs.tracker_domain.model.TrackedFood
import sanmi.labs.tracker_domain.repository.TrackerRepository
import java.time.LocalDate
import kotlin.math.roundToInt

class TrackFoodUseCase(
    private val repository: TrackerRepository
) {
    suspend operator fun invoke(
        trackableFood: TrackableFood,
        amount: Int,
        mealType: MealType,
        date: LocalDate,
    ) {
      repository.insertTrackedFood(
          TrackedFood(
              name = trackableFood.name,
              carbs = ((trackableFood.carbsPer100g / 100f) * amount).roundToInt(),
              protein = ((trackableFood.proteinPer100g / 100f) * amount).roundToInt(),
              fat = ((trackableFood.fatPer100g / 100f) * amount).roundToInt(),
              calories = ((trackableFood.caloriesPer100g / 100f) * amount).roundToInt(),
              imageUrl = trackableFood.imageUrl,
              mealType = mealType,
              amount = amount,
              date = date
          )
      )
    }
}
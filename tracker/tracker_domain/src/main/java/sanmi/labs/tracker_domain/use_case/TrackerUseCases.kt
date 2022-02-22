package sanmi.labs.tracker_domain.use_case

data class TrackerUseCases(
    val calculateMealNutrientsUseCase: CalculateMealNutrientsUseCase,
    val deleteTrackedFoodUseCase: DeleteTrackedFoodUseCase,
    val getFoodsForDateUseCase: GetFoodsForDateUseCase,
    val searchFoodUseCase: SearchFoodUseCase,
    val trackFoodUseCase: TrackFoodUseCase,
)

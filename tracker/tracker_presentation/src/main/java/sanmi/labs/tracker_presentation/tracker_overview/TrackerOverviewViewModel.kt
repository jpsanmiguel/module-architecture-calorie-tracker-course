package sanmi.labs.tracker_presentation.tracker_overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import sanmi.labs.core.domain.preferences.Preferences
import sanmi.labs.core.navigation.Route
import sanmi.labs.core.util.UiEvent
import sanmi.labs.tracker_domain.use_case.TrackerUseCases
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    preferences: Preferences,
    private val trackerUseCases: TrackerUseCases,
) : ViewModel() {

    var state by mutableStateOf(TrackerOverviewState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getFoodsForDateJob: Job? = null

    init {
        preferences.saveShouldShowOnBoarding(false)
    }

    fun onEvent(trackerOverviewEvent: TrackerOverviewEvent) {
        when (trackerOverviewEvent) {
            is TrackerOverviewEvent.OnAddFoodClicked -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UiEvent.Navigate(
                            route = Route.SEARCH
                                    + "/${trackerOverviewEvent.meal.mealType.name}"
                                    + "/${state.date.dayOfMonth}"
                                    + "/${state.date.monthValue}"
                                    + "/${state.date.year}"
                        )
                    )
                }
            }
            is TrackerOverviewEvent.OnDeleteTrackedFoodClicked -> {
                viewModelScope.launch {
                    trackerUseCases.deleteTrackedFoodUseCase(trackerOverviewEvent.trackedFood)
                    refreshFoods()
                }
            }
            is TrackerOverviewEvent.OnNextDayClicked -> {
                state = state.copy(
                    date = state.date.plusDays(1)
                )
                refreshFoods()
            }
            is TrackerOverviewEvent.OnPreviousDayClicked -> {
                state = state.copy(
                    date = state.date.minusDays(1)
                )
                refreshFoods()
            }
            is TrackerOverviewEvent.OnToggleMealClicked -> {
                state = state.copy(
                    meals = state.meals.map {
                        if (it.name == trackerOverviewEvent.meal.name) {
                            it.copy(isExpanded = !it.isExpanded)
                        } else {
                            it
                        }
                    }
                )
            }
        }
    }

    private fun refreshFoods() {
        getFoodsForDateJob?.cancel()
        getFoodsForDateJob = trackerUseCases
            .getFoodsForDateUseCase(state.date)
            .onEach { foods ->
                val nutrients = trackerUseCases.calculateMealNutrientsUseCase(foods)
                state = state.copy(
                    totalCarbs = nutrients.totalCarbs,
                    totalProtein = nutrients.totalProtein,
                    totalCalories = nutrients.totalCalories,
                    totalFat = nutrients.totalFat,
                    carbsGoal = nutrients.carbsGoal,
                    proteinGoal = nutrients.proteinGoal,
                    fatGoal = nutrients.fatGoal,
                    caloriesGoal = nutrients.caloriesGoal,
                    dayTrackedFoods = foods,
                    meals = state.meals.map {
                        val nutrientsForMeal =
                            nutrients.mealNutrients[it.mealType]
                                ?: return@map it.copy(
                                    carbs = 0,
                                    protein = 0,
                                    fat = 0,
                                    calories = 0,
                                )
                        it.copy(
                            carbs = nutrientsForMeal.carbs,
                            protein = nutrientsForMeal.protein,
                            fat = nutrientsForMeal.fat,
                            calories = nutrientsForMeal.calories,
                        )
                    }
                )
            }
            .launchIn(viewModelScope)
    }
}
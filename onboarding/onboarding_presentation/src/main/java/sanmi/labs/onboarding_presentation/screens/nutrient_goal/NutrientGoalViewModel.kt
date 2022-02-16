package sanmi.labs.onboarding_presentation.screens.nutrient_goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import sanmi.labs.core.domain.preferences.Preferences
import sanmi.labs.core.domain.use_case.FilterOutDigitsUseCase
import sanmi.labs.core.navigation.Route
import sanmi.labs.core.util.UiEvent
import sanmi.labs.onboarding_domain.use_case.ValidateNutrientsUseCase
import javax.inject.Inject

@HiltViewModel
class NutrientGoalViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigitsUseCase: FilterOutDigitsUseCase,
    private val validateNutrientsUseCase: ValidateNutrientsUseCase,
) : ViewModel() {

    var state by mutableStateOf(NutrientGoalState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NutrientGoalEvent) {
        when (event) {
            is NutrientGoalEvent.OnCarbRatioEntered -> {
                state = state.copy(
                    carbsRatio = filterOutDigitsUseCase(event.ratio)
                )
            }
            is NutrientGoalEvent.OnProteinRatioEntered -> {
                state = state.copy(
                    proteinRatio = filterOutDigitsUseCase(event.ratio)
                )
            }
            is NutrientGoalEvent.OnFatRatioEntered -> {
                state = state.copy(
                    fatRatio = filterOutDigitsUseCase(event.ratio)
                )
            }
            is NutrientGoalEvent.OnNextClick -> {
                val result = validateNutrientsUseCase(
                    state.carbsRatio,
                    state.proteinRatio,
                    state.fatRatio
                )
                when (result) {
                    is ValidateNutrientsUseCase.Result.Success -> {
                        preferences.saveCarbRatio(result.carbsRatio)
                        preferences.saveProteinRatio(result.proteinRatio)
                        preferences.saveFatRatio(result.fatRatio)
                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.Navigate(Route.TRACKER_OVERVIEW))
                        }
                    }
                    is ValidateNutrientsUseCase.Result.Error -> {
                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.ShowSnackbar(result.message))
                        }
                    }
                }
            }
        }
    }
}
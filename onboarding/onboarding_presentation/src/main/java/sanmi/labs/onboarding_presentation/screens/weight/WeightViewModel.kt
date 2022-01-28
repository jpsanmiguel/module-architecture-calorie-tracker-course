package sanmi.labs.onboarding_presentation.screens.weight

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
import sanmi.labs.core.navigation.Route
import sanmi.labs.core.util.UiEvent
import sanmi.labs.core.util.UiText
import sanmi.labs.onboarding_presentation.R
import javax.inject.Inject

const val INITIAL_WEIGHT_VALUE = "60.0"

@HiltViewModel
class WeightViewModel @Inject constructor(
    private val preferences: Preferences,
): ViewModel() {

    var weight by mutableStateOf(INITIAL_WEIGHT_VALUE)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onWeightEnter(weight: String) {
        if (weight.length <= 5) {
            this.weight = weight
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val weightInt = weight.toFloatOrNull() ?: kotlin.run {
                _uiEvent.send(
                    UiEvent.ShowSnackbar(
                        UiText.StringResource(R.string.error_weight_cant_be_empty)
                    )
                )
                return@launch
            }

            preferences.saveWeight(weightInt)
            _uiEvent.send(UiEvent.Navigate(Route.ACTIVITY))
        }
    }
}
package sanmi.labs.onboarding_presentation.screens.height

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
import sanmi.labs.core.util.UiText
import sanmi.labs.onboarding_presentation.R
import javax.inject.Inject

const val INITIAL_HEIGHT_VALUE = "170"

@HiltViewModel
class HeightViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigitsUseCase: FilterOutDigitsUseCase,
): ViewModel() {

    var height by mutableStateOf(INITIAL_HEIGHT_VALUE)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onHeightEnter(height: String) {
        if (height.length <= 3) {
            this.height = filterOutDigitsUseCase(height)
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val heightInt = height.toIntOrNull() ?: kotlin.run {
                _uiEvent.send(
                    UiEvent.ShowSnackbar(
                        UiText.StringResource(R.string.error_height_cant_be_empty)
                    )
                )
                return@launch
            }

            preferences.saveHeight(heightInt)
            _uiEvent.send(UiEvent.Navigate(Route.WEIGHT))
        }
    }
}
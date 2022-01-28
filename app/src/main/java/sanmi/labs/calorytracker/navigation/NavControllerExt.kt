package sanmi.labs.calorytracker.navigation

import androidx.navigation.NavController
import sanmi.labs.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(event.route)
}
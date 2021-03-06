package sanmi.labs.tracker_presentation.tracker_overview

import sanmi.labs.tracker_domain.model.TrackedFood

sealed class TrackerOverviewEvent {
    object OnNextDayClicked: TrackerOverviewEvent()
    object OnPreviousDayClicked: TrackerOverviewEvent()
    data class OnToggleMealClicked(val meal: Meal): TrackerOverviewEvent()
    data class OnDeleteTrackedFoodClicked(val trackedFood: TrackedFood): TrackerOverviewEvent()
    data class OnAddFoodClicked(val meal: Meal): TrackerOverviewEvent()
}

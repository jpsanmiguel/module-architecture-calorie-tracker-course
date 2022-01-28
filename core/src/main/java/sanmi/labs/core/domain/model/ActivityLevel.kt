package sanmi.labs.core.domain.model

const val LOW = "low"
const val MEDIUM = "medium"
const val HIGH = "high"

sealed class ActivityLevel(val name: String) {

    object Low: ActivityLevel(LOW)
    object Medium: ActivityLevel(MEDIUM)
    object High: ActivityLevel(HIGH)

    companion object {
        fun fromString(name: String): ActivityLevel {
            return when(name) {
                LOW -> Low
                MEDIUM -> Medium
                HIGH -> High
                else -> High
            }
        }
    }
}

package sanmi.labs.core.domain.model

const val LOSE_WEIGHT = "lose_weight"
const val KEEP_WEIGHT = "keep_weight"
const val GAIN_WEIGHT = "gain_weight"

sealed class GoalType(val name: String) {

    object LoseWeight: GoalType(LOSE_WEIGHT)
    object KeepWeight: GoalType(KEEP_WEIGHT)
    object GainWeight: GoalType(GAIN_WEIGHT)

    companion object {
        fun fromString(name: String): GoalType {
            return when(name) {
                LOSE_WEIGHT -> LoseWeight
                KEEP_WEIGHT -> KeepWeight
                GAIN_WEIGHT -> GainWeight
                else -> GainWeight
            }
        }
    }
}

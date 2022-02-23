package sanmi.labs.core.domain.preferences

import sanmi.labs.core.domain.model.ActivityLevel
import sanmi.labs.core.domain.model.Gender
import sanmi.labs.core.domain.model.GoalType
import sanmi.labs.core.domain.model.UserInfo

interface Preferences {
    fun saveGender(gender: Gender)
    fun saveAge(age: Int)
    fun saveWeight(weight: Float)
    fun saveHeight(height: Int)
    fun saveActivityLevel(activityLevel: ActivityLevel)
    fun saveGoalType(goalType: GoalType)
    fun saveCarbRatio(carbRatio: Float)
    fun saveProteinRatio(proteinRatio: Float)
    fun saveFatRatio(fatRatio: Float)

    fun loadUserInfo(): UserInfo

    fun saveShouldShowOnBoarding(shouldShowOnBoarding: Boolean)
    fun loadShouldShowOnBoarding(): Boolean

    companion object {
        const val KEY_GENDER = "gender"
        const val KEY_AGE = "age"
        const val KEY_WEIGHT = "weight"
        const val KEY_HEIGHT = "height"
        const val KEY_ACTIVITY_LEVEL = "activity_level"
        const val KEY_GOAL_TYPE = "goal_type"
        const val KEY_CARB_RATIO = "carb_ratio"
        const val KEY_PROTEIN_RATIO = "protein_ratio"
        const val KEY_FAT_RATIO = "fat_ratio"
        const val KEY_SHOULD_SHOW_ON_BOARDING = "should_show_on_boarding"
    }
}
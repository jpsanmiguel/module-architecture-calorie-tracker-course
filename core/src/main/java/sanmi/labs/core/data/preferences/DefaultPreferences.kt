package sanmi.labs.core.data.preferences

import android.content.SharedPreferences
import sanmi.labs.core.domain.model.*
import sanmi.labs.core.domain.preferences.Preferences

class DefaultPreferences(
    private val sharedPreferences: SharedPreferences
) : Preferences {
    override fun saveGender(gender: Gender) {
        sharedPreferences.edit().putString(Preferences.KEY_GENDER, gender.name).apply()
    }

    override fun saveAge(age: Int) {
        sharedPreferences.edit().putInt(Preferences.KEY_HEIGHT, age).apply()
    }

    override fun saveWeight(weight: Float) {
        sharedPreferences.edit().putFloat(Preferences.KEY_WEIGHT, weight).apply()
    }

    override fun saveHeight(height: Int) {
        sharedPreferences.edit().putInt(Preferences.KEY_HEIGHT, height).apply()
    }

    override fun saveActivityLevel(activityLevel: ActivityLevel) {
        sharedPreferences.edit().putString(Preferences.KEY_ACTIVITY_LEVEL, activityLevel.name)
            .apply()
    }

    override fun saveGoalType(goalType: GoalType) {
        sharedPreferences.edit().putString(Preferences.KEY_GOAL_TYPE, goalType.name).apply()
    }

    override fun saveCarbRatio(carbRatio: Float) {
        sharedPreferences.edit().putFloat(Preferences.KEY_CARB_RATIO, carbRatio).apply()
    }

    override fun saveProteinRatio(proteinRatio: Float) {
        sharedPreferences.edit().putFloat(Preferences.KEY_PROTEIN_RATIO, proteinRatio).apply()
    }

    override fun saveFatRatio(fatRatio: Float) {
        sharedPreferences.edit().putFloat(Preferences.KEY_FAT_RATIO, fatRatio).apply()
    }

    override fun loadUserInfo(): UserInfo {
        val age = sharedPreferences.getInt(Preferences.KEY_AGE, -1)
        val height = sharedPreferences.getInt(Preferences.KEY_HEIGHT, -1)
        val weight = sharedPreferences.getFloat(Preferences.KEY_WEIGHT, -1f)
        val genderString = sharedPreferences.getString(Preferences.KEY_GENDER, null)
        val activityLevelString = sharedPreferences
            .getString(Preferences.KEY_ACTIVITY_LEVEL, null)
        val goalType = sharedPreferences.getString(Preferences.KEY_GOAL_TYPE, null)
        val carbRatio = sharedPreferences.getFloat(Preferences.KEY_CARB_RATIO, -1f)
        val proteinRatio = sharedPreferences.getFloat(Preferences.KEY_PROTEIN_RATIO, -1f)
        val fatRatio = sharedPreferences.getFloat(Preferences.KEY_FAT_RATIO, -1f)

        return UserInfo(
            gender = Gender.fromString(genderString ?: MALE),
            age = age,
            weight = weight,
            height = height,
            activityLevel = ActivityLevel.fromString(activityLevelString ?: MEDIUM),
            goalType = GoalType.fromString(goalType ?: KEEP_WEIGHT),
            carbRatio = carbRatio,
            proteinRatio = proteinRatio,
            fatRatio = fatRatio
        )
    }
}
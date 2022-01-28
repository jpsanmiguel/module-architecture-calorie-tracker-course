package sanmi.labs.core.domain.model

const val MALE = "male"
const val FEMALE = "female"

sealed class Gender(val name: String) {
    object Male: Gender(MALE)
    object Female: Gender(FEMALE)

    companion object {
        fun fromString(name: String): Gender {
            return when(name) {
                MALE -> Male
                FEMALE -> Female
                else -> Female
            }
        }
    }
}

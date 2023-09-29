package com.cradlesoft.medreminder.android.account.viewmodel

import java.time.LocalDate

data class AccountState(
    val name: String = "",
    val lastName: String = "",
    val dateOfBirth: LocalDate = LocalDate.now(),
    val gender: Gender = Gender.UNSPECIFIED,
    val showDateSelector: Boolean = false
)

enum class Gender(val value: String) {
    MALE("Hombre"),
    FEMALE("Mujer"),
    UNSPECIFIED("Sin especificar")
}

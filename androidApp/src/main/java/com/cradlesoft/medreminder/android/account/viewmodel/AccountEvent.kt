package com.cradlesoft.medreminder.android.account.viewmodel

import java.time.LocalDate

sealed class AccountEvent {
    data class OnNameChanged(val name: String): AccountEvent()
    data class OnLastNameChanged(val lastName: String): AccountEvent()
    data class OnDateSelected(val date: LocalDate): AccountEvent()
    data class OnGenderSelected(val gender: Gender): AccountEvent()
    data class EnableSelector(val isEnabled: Boolean): AccountEvent()
}
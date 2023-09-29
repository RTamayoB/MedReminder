package com.cradlesoft.medreminder.android.account.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AccountViewModel: ViewModel() {

    private val _state = MutableStateFlow(AccountState())
    val state: StateFlow<AccountState> = _state.asStateFlow()

    fun onEvent(event: AccountEvent) {
        when(event) {
            is AccountEvent.OnDateSelected -> {
                _state.update {
                    it.copy(
                        dateOfBirth = event.date
                    )
                }
            }
            is AccountEvent.OnGenderSelected -> {
                _state.update {
                    it.copy(
                        gender = event.gender
                    )
                }
            }
            is AccountEvent.OnLastNameChanged -> {
                _state.update {
                    it.copy(
                        lastName = event.lastName
                    )
                }
            }
            is AccountEvent.OnNameChanged -> {
                _state.update {
                    it.copy(
                       name = event.name
                    )
                }
            }
            is AccountEvent.EnableSelector -> {
                _state.update {
                    it.copy(
                        showDateSelector = event.isEnabled
                    )
                }
            }
        }
    }
}
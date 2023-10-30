package com.cradlesoft.medreminder.android.presciption.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cradlesoft.medreminder.core.domain.PrescriptionsDataSource
import com.cradlesoft.medreminder.prescription.list.ui.PrescriptionListEvent
import com.cradlesoft.medreminder.prescription.list.ui.PrescriptionListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PrescriptionListViewModel(
    private val prescriptionsDataSource: PrescriptionsDataSource
): ViewModel() {
    private val _state = MutableStateFlow(PrescriptionListState())

    val state = combine(
        _state,
        prescriptionsDataSource.getPrescriptions()
    ) { state, prescriptions ->
        state.copy(
            prescriptions = prescriptions
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), PrescriptionListState())

    fun onEvent(event: PrescriptionListEvent) {
        when (event) {
            is PrescriptionListEvent.CreateMockPrescription -> {
                viewModelScope.launch {
                    prescriptionsDataSource.insertPrescription(event.newPrescription)
                }
            }
            else -> Unit
        }
    }
}
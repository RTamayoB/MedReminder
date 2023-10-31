package com.cradlesoft.medreminder.android.presciption.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cradlesoft.medreminder.core.domain.PrescriptionsDataSource
import com.cradlesoft.medreminder.prescription.list.ui.PrescriptionListEvent
import com.cradlesoft.medreminder.prescription.list.ui.PrescriptionListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PrescriptionListViewModel(
    private val prescriptionsDataSource: PrescriptionsDataSource
): ViewModel() {
    private val _state = MutableStateFlow(PrescriptionListState())
    val state: StateFlow<PrescriptionListState> = _state.asStateFlow()

    init {
        getPrescriptions()
    }

    fun onEvent(event: PrescriptionListEvent) {
        when (event) {
            is PrescriptionListEvent.CreateMockPrescription -> {
                viewModelScope.launch {
                    prescriptionsDataSource.insertPrescription(event.newPrescription)
                }
            }
            is PrescriptionListEvent.GetPrescriptions -> {
                getPrescriptions()
            }
        }
    }

    private fun getPrescriptions() {
        viewModelScope.launch {
            prescriptionsDataSource.getPrescriptions().collect { prescriptions ->
                _state.update {
                    it.copy(
                        prescriptions = prescriptions
                    )
                }
            }
        }
    }
}
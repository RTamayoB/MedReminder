package com.cradlesoft.medreminder.android.presciption.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cradlesoft.medreminder.core.domain.PrescriptionsDataSource
import com.cradlesoft.medreminder.prescription.detail.ui.PrescriptionDetailEvent
import com.cradlesoft.medreminder.prescription.detail.ui.PrescriptionDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class PrescriptionDetailViewModel(
    prescriptionsDataSource: PrescriptionsDataSource
): ViewModel() {

    private val _state = MutableStateFlow(PrescriptionDetailState())

    val state = combine(
        _state,
        prescriptionsDataSource.getPrescriptionById(1)
    ) { state, prescription ->
        state.copy(
            prescription = prescription
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), PrescriptionDetailState())

    fun onEvent(event: PrescriptionDetailEvent) {
        when (event) {
            else -> Unit
        }
    }
}
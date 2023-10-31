package com.cradlesoft.medreminder.android.presciption.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cradlesoft.medreminder.core.domain.PrescriptionsDataSource
import com.cradlesoft.medreminder.core.domain.models.Prescription
import com.cradlesoft.medreminder.prescription.detail.ui.PrescriptionDetailEvent
import com.cradlesoft.medreminder.prescription.detail.ui.PrescriptionDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PrescriptionDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val prescriptionsDataSource: PrescriptionsDataSource
): ViewModel() {

    private val userId: String = checkNotNull(savedStateHandle["id"])

    private val _state = MutableStateFlow(PrescriptionDetailState())
    val state: StateFlow<PrescriptionDetailState> = _state.asStateFlow()

    private var previousPrescriptionState = Prescription()

    init {
        viewModelScope.launch {
            prescriptionsDataSource.getPrescriptionById(userId.toLong()).collect { prescription ->
                prescription?.let {
                    _state.update {
                        it.copy(
                            prescription = prescription
                        )
                    }
                    previousPrescriptionState = prescription
                }
            }
        }
    }

    fun onEvent(event: PrescriptionDetailEvent) {
        when (event) {
            is PrescriptionDetailEvent.EditModeOn -> {
                previousPrescriptionState = state.value.prescription
                _state.update {
                    it.copy(
                        isEditMode = true
                    )
                }
            }
            is PrescriptionDetailEvent.CancelEdit -> {
                _state.update {
                    it.copy(
                        isEditMode = false,
                        prescription = previousPrescriptionState
                    )
                }
            }
            is PrescriptionDetailEvent.SavePrescription -> {
                previousPrescriptionState = state.value.prescription
                _state.update {
                    it.copy(
                        isEditMode = false,
                        prescription = state.value.prescription
                    )
                }
                updatePrescription(state.value.prescription)
            }
            is PrescriptionDetailEvent.DeletePrescription -> {
                deletePrescription(state.value.prescription.id!!)
            }
            is PrescriptionDetailEvent.SetPrescriptionName -> {
                _state.update { currentState ->
                    currentState.copy(
                        prescription = currentState.prescription.copy(
                            name = event.prescriptionName
                        )
                    )
                }
            }
        }
    }

    private fun updatePrescription(prescription: Prescription) {
        viewModelScope.launch {
            prescriptionsDataSource.updatePrescription(prescription)
        }
    }

    private fun deletePrescription(id: Long) {
        viewModelScope.launch {
            prescriptionsDataSource.deletePrescription(id)
        }
    }
}
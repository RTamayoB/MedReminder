package com.cradlesoft.medreminder.android.presciption.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cradlesoft.medreminder.core.domain.PrescriptionsDataSource
import com.cradlesoft.medreminder.core.domain.models.Medicine
import com.cradlesoft.medreminder.core.domain.models.Prescription
import com.cradlesoft.medreminder.prescription.MedicineBuilder
import com.cradlesoft.medreminder.prescription.detail.ui.PrescriptionDetailEvent
import com.cradlesoft.medreminder.prescription.detail.ui.PrescriptionDetailState
import kotlinx.coroutines.Dispatchers
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
            userId.toLongOrNull()?.let { id ->
                prescriptionsDataSource.getPrescriptionById(id).collect { prescription ->
                    prescription?.let {
                        _state.update {
                            it.copy(
                                prescription = prescription
                            )
                        }
                        previousPrescriptionState = prescription
                    }
                }
            } ?: _state.update { it.copy(isEditModeEnabled = true) }
        }
    }

    fun onEvent(event: PrescriptionDetailEvent) {
        when (event) {
            is PrescriptionDetailEvent.EnabledEditMode -> {
                previousPrescriptionState = state.value.prescription
                _state.update {
                    it.copy(
                        isEditModeEnabled = true
                    )
                }
            }
            is PrescriptionDetailEvent.CancelEditMode -> {
                _state.update {
                    it.copy(
                        isEditModeEnabled = false,
                        prescription = previousPrescriptionState
                    )
                }
            }
            is PrescriptionDetailEvent.SavePrescription -> {
                previousPrescriptionState = state.value.prescription
                _state.update {
                    it.copy(
                        isEditModeEnabled = false,
                        prescription = state.value.prescription
                    )
                }
                if (userId == "new") {
                    savePrescription(state.value.prescription)
                } else {
                    updatePrescription(state.value.prescription)
                }
            }
            is PrescriptionDetailEvent.OpenMedicine -> {
                _state.update {
                    it.copy(
                        selectedMedicine = event.selectedMedicine
                    )
                }
            }
            is PrescriptionDetailEvent.DeleteMedicine -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteMedicine(event.deletedMedicine)
                }
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
            is PrescriptionDetailEvent.AddMedicineToPrescription -> {
                viewModelScope.launch(Dispatchers.IO) {
                    createAndAddMedicine(event.medicineBuilder)
                }
            }
        }
    }

    private fun savePrescription(prescription: Prescription) {
        viewModelScope.launch {
            prescriptionsDataSource.insertPrescription(prescription)
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

    private fun deleteMedicine(medicine: Medicine) {
        val medicines = _state.value.prescription.medicines.toMutableList()
        medicines.remove(medicine)
        _state.update {
            it.copy(
                prescription = it.prescription.copy(medicines = medicines)
            )
        }
    }

    private fun createAndAddMedicine(medicineBuilder: MedicineBuilder) {
        val newMedicine = medicineBuilder.build()
        val prescription = _state.value.prescription
        val newMedicines = prescription.medicines.toMutableList()
        newMedicines.add(newMedicine)
        _state.update {
            it.copy(
                prescription = it.prescription.copy(medicines = newMedicines)
            )
        }
    }
}
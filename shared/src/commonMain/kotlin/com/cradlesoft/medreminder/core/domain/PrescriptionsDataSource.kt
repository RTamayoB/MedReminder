package com.cradlesoft.medreminder.core.domain

import com.cradlesoft.medreminder.core.domain.models.Prescription
import kotlinx.coroutines.flow.Flow

interface PrescriptionsDataSource {
    fun getPrescriptions(): Flow<List<Prescription>>
    fun getPrescriptionById(id: Long): Flow<Prescription?>
    suspend fun insertPrescription(prescription: Prescription)
    suspend fun updatePrescription(prescription: Prescription)
    suspend fun deletePrescription(id: Long)
}
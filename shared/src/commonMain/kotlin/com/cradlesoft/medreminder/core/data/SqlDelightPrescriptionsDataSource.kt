package com.cradlesoft.medreminder.core.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.cradlesoft.medreminder.core.domain.PrescriptionsDataSource
import com.cradlesoft.medreminder.core.domain.models.Doctor
import com.cradlesoft.medreminder.core.domain.models.Prescription
import com.cradlesoft.medreminder.core.domain.toDoctor
import com.cradlesoft.medreminder.core.domain.toIntakes
import com.cradlesoft.medreminder.core.domain.toMedicines
import com.cradlesoft.medreminder.core.domain.toPrescription
import com.cradlesoft.medreminder.database.PrescriptionsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map

class SqlDelightPrescriptionsDataSource(
    db: PrescriptionsDatabase
): PrescriptionsDataSource {

    private val queries = db.prescriptionDatabaseQueries
    override fun getPrescriptions(): Flow<List<Prescription>> {
        return queries
            .getAllPrescriptions()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { prescriptionEntities ->
                prescriptionEntities.map { prescriptionEntity ->
                    var doctor: Doctor? = null
                    prescriptionEntity.doctor_id?.let { doctorId ->
                        queries.getDoctorById(doctorId).asFlow().mapToOneOrNull(Dispatchers.IO).collectLatest {
                            doctor = it?.toDoctor()
                        }
                    }
                    prescriptionEntity.toPrescription().copy(
                        doctor = doctor,
                        medicines = queries
                            .getMedicinesByPrescriptionId(prescriptionEntity.id)
                            .executeAsList()
                            .toMedicines().map { medicine ->
                                medicine.copy(
                                    intakes = queries.getIntakesByMedicineId(medicine.id)
                                        .executeAsList()
                                        .toIntakes()
                                )
                            }
                    )
                }
            }
    }

    override fun getPrescriptionById(id: Long): Flow<Prescription?> {
        return queries
            .getPrescriptionById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { prescriptionEntity ->
                prescriptionEntity?.toPrescription()?.copy(
                    medicines = queries.getMedicinesByPrescriptionId(id)
                        .executeAsList()
                        .toMedicines().map { medicine ->
                            medicine.copy(
                                intakes = queries.getIntakesByMedicineId(medicine.id)
                                    .executeAsList()
                                    .toIntakes()
                            )
                        }
                )
            }
    }

    override suspend fun insertPrescription(prescription: Prescription) {
       queries.insertPrescription(
           name = prescription.name,
           doctor_id = prescription.doctor?.id,
       )
    }

    override suspend fun deletePrescription(id: Long) {
        queries.deletePresctiption(id)
    }
}
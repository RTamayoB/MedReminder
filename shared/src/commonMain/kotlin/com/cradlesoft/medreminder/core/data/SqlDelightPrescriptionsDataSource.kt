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
       )
    }

    override suspend fun deletePrescription(id: Long) {
        queries.deletePresctiption(id)
    }

    override suspend fun updatePrescription(prescription: Prescription) {
        prescription.id?.let {
            queries.updatePresctiption(prescription.name, it)
        }
        val medicines = prescription.medicines
        for (medicine in medicines) {
            queries.insertMedicine(
                id = medicine.id,
                prescription_id = prescription.id,
                name = medicine.name,
                type = medicine.type.name.lowercase(),
                method = medicine.method,
                start_intake = medicine.startOfIntake.toString(),
                end_intake = medicine.endOfIntake.toString()
            )
            val medicineId = queries.lastInsertRowId().executeAsOneOrNull()
            for (intake in medicine.intakes) {
                queries.insertIntake(
                    id = intake.id,
                    medicine_id = medicineId,
                    hour = intake.hour.toString(),
                    intake_amount = intake.intakeAmount.toDouble()
                )
            }
        }
    }
}
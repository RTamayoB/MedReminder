package com.cradlesoft.medreminder.core.domain

import com.cradlesoft.medreminder.core.domain.models.Intake
import com.cradlesoft.medreminder.core.domain.models.Medicine
import com.cradlesoft.medreminder.core.domain.models.MedicineType
import com.cradlesoft.medreminder.core.domain.models.Prescription
import database.IntakeEntity
import database.MedicineEntity
import database.PrescriptionEntity
import kotlinx.datetime.LocalTime

suspend fun PrescriptionEntity.toPrescription(): Prescription {
    return Prescription(
        id = id,
        name = name,
        medicines = emptyList()
    )
}

suspend fun List<PrescriptionEntity>.toPrescriptions(): List<Prescription> {
    return map { it.toPrescription() }
}

suspend fun Prescription.toPrescriptionEntity(): PrescriptionEntity {
    return PrescriptionEntity(
        id = id ?: 0,
        name = name
    )
}

suspend fun MedicineEntity.toMedicine(): Medicine {
    return Medicine(
        id = id,
        name = name,
        type = when(type) {
            "tablets" -> MedicineType.TABLETS
            "pills" -> MedicineType.PILLS
            "drops" -> MedicineType.DROPS
            "solution" -> MedicineType.SOLUTION
            else -> MedicineType.NONE
        },
        intakes = emptyList()
    )
}

suspend fun List<MedicineEntity>.toMedicines(): List<Medicine> {
    return map { it.toMedicine() }
}

suspend fun IntakeEntity.toIntake(): Intake {
    return Intake(
        id = id,
        hour = LocalTime(0,0),
        intakeAmount = intake_amount.toFloat()
    )
}

suspend fun List<IntakeEntity>.toIntakes(): List<Intake> {
    return map { it.toIntake() }
}
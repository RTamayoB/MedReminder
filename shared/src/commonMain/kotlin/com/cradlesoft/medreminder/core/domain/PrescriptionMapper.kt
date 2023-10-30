package com.cradlesoft.medreminder.core.domain

import com.cradlesoft.medreminder.core.domain.models.Doctor
import com.cradlesoft.medreminder.core.domain.models.Intake
import com.cradlesoft.medreminder.core.domain.models.Medicine
import com.cradlesoft.medreminder.core.domain.models.MedicineType
import com.cradlesoft.medreminder.core.domain.models.Prescription
import database.DoctorEntity
import database.IntakeEntity
import database.MedicineEntity
import database.PrescriptionEntity
import kotlinx.datetime.LocalTime

fun DoctorEntity.toDoctor(): Doctor {
    return Doctor(
        id = id,
        name = name,
        specialty = specialty
    )
}

fun PrescriptionEntity.toPrescription(): Prescription {
    return Prescription(
        id = id,
        name = name,
        doctor = Doctor(id = null, name = "", specialty = ""),
        medicines = emptyList()
    )
}

fun List<PrescriptionEntity>.toPrescriptions(): List<Prescription> {
    return map { it.toPrescription() }
}

fun MedicineEntity.toMedicine(): Medicine {
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

fun List<MedicineEntity>.toMedicines(): List<Medicine> {
    return map { it.toMedicine() }
}

fun IntakeEntity.toIntake(): Intake {
    return Intake(
        id = id,
        hour = LocalTime(0,0),
        intakeAmount = intake_amount.toFloat()
    )
}

fun List<IntakeEntity>.toIntakes(): List<Intake> {
    return map { it.toIntake() }
}
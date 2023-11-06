package com.cradlesoft.medreminder.core.domain

import com.cradlesoft.medreminder.core.domain.models.Doctor
import com.cradlesoft.medreminder.core.domain.models.Schedule
import com.cradlesoft.medreminder.core.domain.models.Medicine
import com.cradlesoft.medreminder.core.domain.models.MedicineType
import com.cradlesoft.medreminder.core.domain.models.Prescription
import database.DoctorEntity
import database.MedicineEntity
import database.PrescriptionEntity
import database.ScheduleEntity
import kotlinx.datetime.LocalDate
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
        method = method,
        startOfIntake = LocalDate.parse(start_intake),
        endOfIntake = LocalDate.parse(end_intake),
        schedules = emptyList()
    )
}

fun List<MedicineEntity>.toMedicines(): List<Medicine> {
    return map { it.toMedicine() }
}

fun ScheduleEntity.toSchedule(): Schedule {
    return Schedule(
        id = id,
        hour = LocalTime.parse(hour),
        dosage = dosage.toFloat()
    )
}

fun List<ScheduleEntity>.toSchedules(): List<Schedule> {
    return map { it.toSchedule() }
}
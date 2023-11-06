package com.cradlesoft.medreminder.core.domain.models

data class Prescription(
    val id: Long? = null,
    val name: String = "",
    val doctor: Doctor? = null,
    val medicines: List<Medicine> = emptyList()
) {
    fun getTotalIntake(): Float {
        var totalDosage = 0.0F
        for (medicine in medicines) {
            for (schedule in medicine.schedules) {
                totalDosage += schedule.dosage
            }
        }
        return totalDosage / 100F
    }
}
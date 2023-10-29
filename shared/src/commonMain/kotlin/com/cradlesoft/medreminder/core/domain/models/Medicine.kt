package com.cradlesoft.medreminder.core.domain.models

data class Medicine(
    val id: Long?,
    val name: String,
    val type: MedicineType,
    val intakes: List<Intake>
)

enum class MedicineType {
    TABLETS,
    PILLS,
    SOLUTION,
    DROPS,
    NONE
}
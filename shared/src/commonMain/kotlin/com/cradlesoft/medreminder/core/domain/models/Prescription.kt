package com.cradlesoft.medreminder.core.domain.models

data class Prescription(
    val id: Long?,
    val name: String,
    val medicines: List<Medicine>
)
package com.cradlesoft.medreminder.core.domain.models

data class Prescription(
    val id: Long? = null,
    val name: String,
    val doctor: Doctor?,
    val medicines: List<Medicine>
)
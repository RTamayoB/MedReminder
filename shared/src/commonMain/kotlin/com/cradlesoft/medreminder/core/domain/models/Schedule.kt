package com.cradlesoft.medreminder.core.domain.models

import kotlinx.datetime.LocalTime

data class Schedule(
    val id: Long? = null,
    val hour: LocalTime,
    val dosage: Float
)
package com.cradlesoft.medreminder.core.domain.models

import kotlinx.datetime.LocalTime

data class Intake(
    val id: Long? = null,
    val hour: LocalTime,
    val intakeAmount: Float
)
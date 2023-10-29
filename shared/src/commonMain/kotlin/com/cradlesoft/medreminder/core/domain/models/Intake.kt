package com.cradlesoft.medreminder.core.domain.models

import kotlinx.datetime.LocalTime

data class Intake(
    val id: Long?,
    val hour: LocalTime,
    val intakeAmount: Float
)
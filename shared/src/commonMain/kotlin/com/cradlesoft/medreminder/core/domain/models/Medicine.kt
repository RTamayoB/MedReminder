package com.cradlesoft.medreminder.core.domain.models

import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

data class Medicine(
    val id: Long? = null,
    val name: String = "",
    val type: MedicineType = MedicineType.TABLETS,
    val method: String = "",
    val commonIntake : Float = 0.0F, //Temp String?
    val interval: Int = 0, //Temp String
    val days: Int = 0, //Temp String
    val startOfIntake: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val endOfIntake: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.plus(
        DatePeriod(days = 5)
    ),
    val intakes: List<Intake> = emptyList()
) {
    fun getPeriodInDays(): Int {
        return startOfIntake.daysUntil(endOfIntake)
    }
}

enum class MedicineType {
    TABLETS,
    PILLS,
    SOLUTION,
    DROPS,
    NONE
}
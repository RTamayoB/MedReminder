package com.cradlesoft.medreminder.core.domain.models

import com.cradlesoft.medreminder.core.utils.timeBetween
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.DateTimeUnit

data class Medicine(
    val id: Long? = null,
    val name: String = "",
    val type: MedicineType = MedicineType.TABLETS,
    val method: String = "specific",
    val startOfIntake: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val endOfIntake: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.plus(
        DatePeriod(days = 5)
    ),
    val intakes: List<Intake> = emptyList()
) {
    fun getPeriodInDays(): Int {
        return startOfIntake.daysUntil(endOfIntake)
    }

    fun getNextIntake(): String? {
        val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
        if (intakes.isEmpty()) {
            return null
        }

        var closestIntake = intakes.first()
        var timeDiff = currentTime.timeBetween(closestIntake.hour, DateTimeUnit.MINUTE)

        for (intake in intakes) {
            val diff = currentTime.timeBetween(intake.hour, DateTimeUnit.MINUTE)
            if (diff < 0) {
                continue
            }

            if (diff < timeDiff) {
                closestIntake = intake
                timeDiff = diff
            }
        }
        return closestIntake.hour.toString()
    }
}

enum class MedicineType {
    TABLETS,
    PILLS,
    SOLUTION,
    DROPS,
    NONE
}
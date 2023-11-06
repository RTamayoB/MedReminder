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
    val schedules: List<Schedule> = emptyList()
) {
    fun getPeriodInDays(): Int {
        return startOfIntake.daysUntil(endOfIntake)
    }

    fun getNextSchedule(): String? {
        val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
        if (schedules.isEmpty()) {
            return null
        }

        var closestSchedule = schedules.first()
        var currentTimeDiff = currentTime.timeBetween(closestSchedule.hour, DateTimeUnit.MINUTE)

        for (schedule in schedules) {
            val newDiff = currentTime.timeBetween(schedule.hour, DateTimeUnit.MINUTE)
            if (newDiff > currentTimeDiff && newDiff > 0) {
                closestSchedule = schedule
            }
            currentTimeDiff = newDiff
        }

        return closestSchedule.hour.toString()
    }
}

enum class MedicineType {
    TABLETS,
    PILLS,
    SOLUTION,
    DROPS,
    NONE
}
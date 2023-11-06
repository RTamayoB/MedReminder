package com.cradlesoft.medreminder.prescription

import com.cradlesoft.medreminder.core.domain.models.Schedule
import com.cradlesoft.medreminder.core.domain.models.Medicine
import com.cradlesoft.medreminder.core.domain.models.MedicineType
import com.cradlesoft.medreminder.core.utils.minus
import com.cradlesoft.medreminder.core.utils.plus
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atDate
import kotlinx.datetime.toLocalDateTime

data class MedicineBuilder(
    var id: Long? = null,
    var name: String = "",
    var type: MedicineType = MedicineType.NONE,
    var commonDosage: Float = 0.0F,
    var days: Int = 0,
    var startOfIntake: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    var endOfIntake: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    var method: ScheduleMethod? = null
) {
    fun build(): Medicine {
        val createdSchedules: List<Schedule> = when(method) {
            is ScheduleMethod.Interval -> calculateFromInterval(method as ScheduleMethod.Interval)
            is ScheduleMethod.Meals -> calculateFromMeals(method as ScheduleMethod.Meals)
            is ScheduleMethod.Specific -> (method as ScheduleMethod.Specific).intakes
            else -> emptyList()
        }
        return Medicine(
            id = id,
            name = name,
            type = type,
            method = when(method) {
                is ScheduleMethod.Interval -> "interval"
                is ScheduleMethod.Meals -> "meals"
                is ScheduleMethod.Specific -> "specific"
                else -> ""
            },
            startOfIntake = startOfIntake,
            endOfIntake = endOfIntake,
            schedules = createdSchedules
        )
    }

    private fun calculateFromInterval(method: ScheduleMethod.Interval): List<Schedule> {
        val schedules = mutableListOf<Schedule>()
        var currentTime = LocalTime(8,0).atDate(0,1,1)
        val nextDay = LocalTime(0,0).atDate(0,1,2)
        while (currentTime < nextDay) {
            schedules.add(Schedule(hour = currentTime.time, dosage = commonDosage))
            currentTime = currentTime.plus(method.interval, DateTimeUnit.HOUR)
        }
        return schedules
    }

    private fun calculateFromMeals(method: ScheduleMethod.Meals): List<Schedule> {
        val schedules = mutableListOf<Schedule>()
        for (meal in method.mealsSelected) {
            val hour: LocalTime = when(meal.mealType) {
                MealType.BREAKFAST -> LocalTime.parse("10:00:00")
                MealType.LUNCH -> LocalTime.parse("14:00:00")
                MealType.DINNER -> LocalTime.parse("22:00:00")
            }
            when(meal.mealMoment) {
                MealMoment.BEFORE -> schedules.add(Schedule(hour = hour.minus(30, DateTimeUnit.MINUTE), dosage = commonDosage))
                MealMoment.DURING -> schedules.add(Schedule(hour = hour, dosage = commonDosage))
                MealMoment.AFTER -> schedules.add(Schedule(hour = hour.plus(30, DateTimeUnit.MINUTE), dosage = commonDosage))
                MealMoment.BEFORE_AND_AFTER -> {
                    schedules.add(Schedule(hour = hour.minus(30, DateTimeUnit.MINUTE), dosage = commonDosage))
                    schedules.add(Schedule(hour = hour.plus(30, DateTimeUnit.MINUTE), dosage = commonDosage))
                }
            }
        }
        return schedules
    }
}

sealed interface ScheduleMethod {
    data class Interval(var interval: Long = 0): ScheduleMethod
    data class Meals(var mealsSelected: List<Meal>): ScheduleMethod
    data class Specific(var intakes: List<Schedule>): ScheduleMethod
}

data class Meal(
    val mealType: MealType,
    val mealMoment: MealMoment
)

enum class MealType {
    BREAKFAST,
    LUNCH,
    DINNER
}

enum class MealMoment {
    BEFORE,
    DURING,
    AFTER,
    BEFORE_AND_AFTER,
}
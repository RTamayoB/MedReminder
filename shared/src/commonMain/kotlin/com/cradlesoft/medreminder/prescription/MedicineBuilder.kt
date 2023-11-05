package com.cradlesoft.medreminder.prescription

import com.cradlesoft.medreminder.core.domain.models.Intake
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
    var commonIntake: Float = 0.0F,
    var days: Int = 0,
    var startOfIntake: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    var endOfIntake: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    var method: IntakeMethod? = null
) {
    fun build(): Medicine {
        val createdIntakes: List<Intake> = when(method) {
            is IntakeMethod.Interval -> calculateFromInterval(method as IntakeMethod.Interval)
            is IntakeMethod.Meals -> calculateFromMeals(method as IntakeMethod.Meals)
            is IntakeMethod.Specific -> (method as IntakeMethod.Specific).intakes
            else -> emptyList()
        }
        return Medicine(
            id = id,
            name = name,
            type = type,
            method = when(method) {
                is IntakeMethod.Interval -> "interval"
                is IntakeMethod.Meals -> "meals"
                is IntakeMethod.Specific -> "specific"
                else -> ""
            },
            startOfIntake = startOfIntake,
            endOfIntake = endOfIntake,
            intakes = createdIntakes
        )
    }

    private fun calculateFromInterval(method: IntakeMethod.Interval): List<Intake> {
        val intakes = mutableListOf<Intake>()
        var currentTime = LocalTime(8,0).atDate(0,1,1)
        val nextDay = LocalTime(0,0).atDate(0,1,2)
        while (currentTime < nextDay) {
            intakes.add(Intake(hour = currentTime.time, intakeAmount = commonIntake))
            currentTime = currentTime.plus(method.interval, DateTimeUnit.HOUR)
        }
        return intakes
    }

    private fun calculateFromMeals(method: IntakeMethod.Meals): List<Intake> {
        val intakes = mutableListOf<Intake>()
        for (meal in method.mealsSelected) {
            val hour: LocalTime = when(meal.mealType) {
                MealType.BREAKFAST -> LocalTime.parse("10:00:00")
                MealType.LUNCH -> LocalTime.parse("14:00:00")
                MealType.DINNER -> LocalTime.parse("22:00:00")
            }
            when(meal.mealMoment) {
                MealMoment.BEFORE -> intakes.add(Intake(hour = hour.minus(30, DateTimeUnit.MINUTE), intakeAmount = commonIntake))
                MealMoment.DURING -> intakes.add(Intake(hour = hour, intakeAmount = commonIntake))
                MealMoment.AFTER -> intakes.add(Intake(hour = hour.plus(30, DateTimeUnit.MINUTE), intakeAmount = commonIntake))
                MealMoment.BEFORE_AND_AFTER -> {
                    intakes.add(Intake(hour = hour.minus(30, DateTimeUnit.MINUTE), intakeAmount = commonIntake))
                    intakes.add(Intake(hour = hour.plus(30, DateTimeUnit.MINUTE), intakeAmount = commonIntake))
                }
            }
        }
        return intakes
    }
}

sealed interface IntakeMethod {
    data class Interval(var interval: Long = 0): IntakeMethod
    data class Meals(var mealsSelected: List<Meal>): IntakeMethod
    data class Specific(var intakes: List<Intake>): IntakeMethod
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
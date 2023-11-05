package com.cradlesoft.medreminder.core.utils

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.until

fun LocalDateTime.plus(value: Long, unit: DateTimeUnit): LocalDateTime {
    val timeZone = TimeZone.currentSystemDefault()
    return this
        .toInstant(timeZone)
        .plus(value, unit, timeZone)
        .toLocalDateTime(timeZone)
}

fun LocalTime.plus(value: Long, unit: DateTimeUnit.TimeBased): LocalTime {
    val timeZone = TimeZone.currentSystemDefault()
    return this
        .toDateTimeNoTime()
        .toInstant(timeZone)
        .plus(value, unit)
        .toLocalDateTime(timeZone)
        .time
}

fun LocalTime.minus(value: Long, unit: DateTimeUnit.TimeBased): LocalTime {
    val timeZone = TimeZone.currentSystemDefault()
    return this
        .toDateTimeNoTime()
        .toInstant(timeZone)
        .minus(value, unit)
        .toLocalDateTime(timeZone)
        .time
}

fun LocalTime.timeBetween(time: LocalTime, unit: DateTimeUnit): Long{
    val timeZone = TimeZone.currentSystemDefault()
    return this
        .toDateTimeNoTime()
        .toInstant(timeZone)
        .until(time.toDateTimeNoTime().toInstant(timeZone), unit, timeZone)
}

fun LocalTime.toDateTimeNoTime(): LocalDateTime{
    return this.atDate(0,1,1)
}
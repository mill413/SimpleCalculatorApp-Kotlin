package top.harumill.calculator.Calculator

import java.time.LocalDate
import java.time.temporal.ChronoUnit

object Calculator {
    lateinit var curDate: LocalDate
    lateinit var startDate: LocalDate
    lateinit var endDate: LocalDate

    fun init() {
        curDate = LocalDate.now()
        startDate = curDate
        endDate = curDate
    }

    fun getDaysBetween(): Long {
        return ChronoUnit.DAYS.between(curDate, endDate)
    }

    fun getDayBefore(dayBefore: Long): LocalDate {
        return startDate.minusDays(dayBefore)
    }

    fun getDayAfter(dayAfter: Long): LocalDate {
        return startDate.plusDays(dayAfter)
    }
}
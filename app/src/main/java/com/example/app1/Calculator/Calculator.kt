package com.example.app1.Calculator

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.Button
import kotlinx.android.synthetic.main.activity_date.*
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.temporal.ChronoUnit
import java.util.*

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
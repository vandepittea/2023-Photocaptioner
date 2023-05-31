package com.example.photocaptioner.data.database

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateConverter {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    fun fromString(value: String): LocalDate = LocalDate.parse(value, formatter)

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String = date.format(formatter)
}
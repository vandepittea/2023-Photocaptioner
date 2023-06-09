package com.example.photocaptioner.data.database

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeConverter {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromString(value: String): LocalDateTime = LocalDateTime.parse(value, formatter)

    @TypeConverter
    fun fromLocalDate(date: LocalDateTime): String = date.format(formatter)
}
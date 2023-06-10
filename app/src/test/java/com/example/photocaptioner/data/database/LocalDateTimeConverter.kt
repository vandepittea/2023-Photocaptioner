package com.example.photocaptioner.data.database

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class LocalDateTimeConverterTest {
    private val converter = LocalDateTimeConverter()

    @Test
    fun testFromString() {
        val dateString = "2023-06-10 15:30:00"
        val expectedDateTime = LocalDateTime.of(2023, 6, 10, 15, 30, 0)

        val convertedDateTime = converter.fromString(dateString)

        assertEquals(expectedDateTime, convertedDateTime)
    }

    @Test
    fun testFromLocalDate() {
        val dateTime = LocalDateTime.of(2023, 6, 10, 15, 30, 0)
        val expectedDateString = "2023-06-10 15:30:00"

        val convertedDateString = converter.fromLocalDate(dateTime)

        assertEquals(expectedDateString, convertedDateString)
    }
}
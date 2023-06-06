package com.example.photocaptioner.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "photos")
data class PhotoDB(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val createdAt: LocalDate,
    val filePath: String,
    val albumId: Long
)

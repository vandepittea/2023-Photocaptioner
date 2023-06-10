package com.example.photocaptioner.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "photos")
data class Photo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val description: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val filePath: String = "",
    var albumId: Long = 0
)
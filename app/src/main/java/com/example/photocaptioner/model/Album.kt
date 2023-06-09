package com.example.photocaptioner.model

import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.photocaptioner.R
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "albums")
data class Album(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val lastChanged: LocalDateTime = LocalDateTime.now()
)



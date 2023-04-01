package com.example.photocaptioner.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import java.util.*

data class Photo(
    @DrawableRes val image: Int,
    @StringRes val description: Int,
    val createdAt: Date
)
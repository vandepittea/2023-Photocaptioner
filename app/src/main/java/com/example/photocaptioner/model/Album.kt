package com.example.photocaptioner.model

import androidx.annotation.StringRes
import com.example.photocaptioner.R
import java.time.LocalDate

data class Album(
    @StringRes val name: Int,
    @StringRes val description: Int,
    val lastChanged: LocalDate,
    val photos: List<Photo>
) {
    val imagePlaceholder: Int
        get() = photos.lastOrNull()?.image ?: R.drawable.default_album_placeholder
}



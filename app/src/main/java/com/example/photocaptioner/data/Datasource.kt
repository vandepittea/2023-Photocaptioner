package com.example.photocaptioner.data

import com.example.photocaptioner.R
import com.example.photocaptioner.model.Album

object Datasource {
    val albumList = listOf(
        Album(R.drawable.paris_france, R.string.paris_france_picture),
        Album(R.drawable.beach_day, R.string.beach_day_picture),
        Album(R.drawable.budapast_hungary, R.string.budapest_hungary_picture),
        Album(R.drawable.ljubljana_slovenia, R.string.ljubljana_slovenia_picture),
        Album(R.drawable.school_project, R.string.school_project_picture)
    )
}
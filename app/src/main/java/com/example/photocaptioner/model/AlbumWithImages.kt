package com.example.photocaptioner.model

import androidx.room.Embedded
import androidx.room.Relation

data class AlbumWithImages(
    @Embedded val album: Album = Album(),
    @Relation(
        parentColumn = "id",
        entityColumn = "albumId"
    )
    val photos: List<Photo> = emptyList()
)

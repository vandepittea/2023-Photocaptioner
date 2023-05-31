package com.example.photocaptioner.data

import androidx.room.Embedded
import androidx.room.Relation

data class AlbumWithImages(
    @Embedded val album: Album,
    @Relation(
        parentColumn = "id",
        entityColumn = "albumId"
    )
    val images: List<Image>
)

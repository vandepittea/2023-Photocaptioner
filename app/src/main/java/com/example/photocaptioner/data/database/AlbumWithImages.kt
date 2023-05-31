package com.example.photocaptioner.data.database

import androidx.room.Embedded
import androidx.room.Relation

data class AlbumWithImages(
    @Embedded val album: AlbumDB,
    @Relation(
        parentColumn = "id",
        entityColumn = "albumId"
    )
    val images: List<PhotoDB>
)

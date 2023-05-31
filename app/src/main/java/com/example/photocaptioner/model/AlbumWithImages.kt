package com.example.photocaptioner.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.photocaptioner.data.database.PhotoDB

data class AlbumWithImages(
    @Embedded val album: Album,
    @Relation(
        parentColumn = "id",
        entityColumn = "albumId"
    )
    val photos: List<Photo>
)

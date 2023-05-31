package com.example.photocaptioner.data.database

import kotlinx.coroutines.flow.Flow

interface ImagesRepository {
    fun getImages(albumId: Long): Flow<List<PhotoDB>>
    fun getImage(imageId: Long): Flow<PhotoDB>
    suspend fun insert(image: PhotoDB)
    suspend fun update(image: PhotoDB)
}
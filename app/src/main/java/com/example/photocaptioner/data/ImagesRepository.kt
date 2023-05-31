package com.example.photocaptioner.data

import kotlinx.coroutines.flow.Flow

interface ImagesRepository {
    fun getImages(albumId: Long): Flow<List<Image>>
    fun getImage(imageId: Long): Flow<Image>
    suspend fun insert(image: Image)
    suspend fun update(image: Image)
}
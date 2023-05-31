package com.example.photocaptioner.data.database

import kotlinx.coroutines.flow.Flow

class OfflineImagesRepository(private val imageDAO: ImageDAO) : ImagesRepository {

    override fun getImages(albumId: Long): Flow<List<PhotoDB>> = imageDAO.getImagesOfAlbum(albumId)

    override fun getImage(imageId: Long): Flow<PhotoDB> = imageDAO.getImage(imageId)

    override suspend fun insert(image: PhotoDB) = imageDAO.insert(image)

    override suspend fun update(image: PhotoDB) = imageDAO.update(image)
}
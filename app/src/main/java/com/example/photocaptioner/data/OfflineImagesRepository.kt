package com.example.photocaptioner.data

import kotlinx.coroutines.flow.Flow

class OfflineImagesRepository(private val imageDAO: ImageDAO) : ImagesRepository {

    override fun getImages(albumId: Long): Flow<List<Image>> = imageDAO.getImagesOfAlbum(albumId)

    override fun getImage(imageId: Long): Flow<Image> = imageDAO.getImage(imageId)

    override suspend fun insert(image: Image) = imageDAO.insert(image)

    override suspend fun update(image: Image) = imageDAO.update(image)
}
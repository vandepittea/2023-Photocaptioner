package com.example.photocaptioner.data.database

import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.AlbumWithImages
import com.example.photocaptioner.model.Photo
import kotlinx.coroutines.flow.Flow

class OfflineAlbumsRepository(private val albumDAO: AlbumDAO) : AlbumsRepository {
    override fun getAlbums(): Flow<List<AlbumWithImages>> = albumDAO.getAlbums()

    override fun getAlbum(albumId: Long): Flow<AlbumWithImages> = albumDAO.getAlbum(albumId)
    override fun getLatestAlbum(): Flow<AlbumWithImages> = albumDAO.getLatestAlbum()

    override fun getPhoto(imageId: Long): Flow<Photo> = albumDAO.getImage(imageId)

    override suspend fun insertAlbum(album: Album) = albumDAO.insertAlbum(album)

    override suspend fun updateAlbum(album: Album) = albumDAO.updateAlbum(album)

    override suspend fun insertPhoto(photo: Photo) = albumDAO.insertPhoto(photo)

    override suspend fun updatePhoto(photo: Photo) = albumDAO.updatePhoto(photo)


}
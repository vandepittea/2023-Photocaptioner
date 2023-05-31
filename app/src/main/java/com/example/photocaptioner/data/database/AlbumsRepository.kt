package com.example.photocaptioner.data.database

import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.AlbumWithImages
import com.example.photocaptioner.model.Photo
import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {
    fun getAlbums(): Flow<List<AlbumWithImages>>
    fun getAlbum(albumId: Long): Flow<AlbumWithImages>
    fun getLatestAlbum(): Flow<AlbumWithImages>
    fun getImage(imageId: Long): Flow<Photo>
    suspend fun insertAlbum(album: Album)
    suspend fun updateAlbum(album: Album)
    suspend fun insertPhoto(photo: Photo)
    suspend fun updatePhoto(photo: Photo)
}
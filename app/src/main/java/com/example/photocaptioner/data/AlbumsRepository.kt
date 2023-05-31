package com.example.photocaptioner.data

import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {
    fun getAlbums(): Flow<List<AlbumWithImages>>
    fun getAlbumWithImages(albumId: Long): Flow<AlbumWithImages>
    suspend fun insert(album: Album)
    suspend fun update(album: Album)
}
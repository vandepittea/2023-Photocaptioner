package com.example.photocaptioner.data.database

import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {
    fun getAlbums(): Flow<List<AlbumWithImages>>
    fun getAlbumWithImages(albumId: Long): Flow<AlbumWithImages>
    suspend fun insert(album: AlbumDB)
    suspend fun update(album: AlbumDB)
}
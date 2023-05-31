package com.example.photocaptioner.data

import kotlinx.coroutines.flow.Flow

class OfflineAlbumsRepository(private val albumDAO: AlbumDAO) : AlbumsRepository {
    override fun getAlbums(): Flow<List<AlbumWithImages>> = albumDAO.getAllAlbums()

    override fun getAlbumWithImages(albumId: Long): Flow<AlbumWithImages> = albumDAO.getAlbum(albumId)

    override suspend fun insert(album: Album) = albumDAO.insert(album)

    override suspend fun update(album: Album) = albumDAO.update(album)

}
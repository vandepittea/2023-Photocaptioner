package com.example.photocaptioner.data.database

import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.AlbumWithImages
import com.example.photocaptioner.model.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime

class TestAlbumsRepository : AlbumsRepository {
    private val albums = mutableListOf(
        Album(
            id = 1,
            name = "Album 1",
            description = "Description 1",
            lastChanged = LocalDateTime.now()
        ),
        Album(
            id = 2,
            name = "Album 2",
            description = "Description 2",
            lastChanged = LocalDateTime.now()
        ),
        Album(
            id = 3,
            name = "Album 3",
            description = "Description 3",
            lastChanged = LocalDateTime.now()
        ),
        Album(
            id = 4,
            name = "Album 4",
            description = "Description 4",
            lastChanged = LocalDateTime.now()
        ),
        Album(
            id = 5,
            name = "Album 5",
            description = "Description 5",
            lastChanged = LocalDateTime.now()
        )
    )
    private val photos = mutableListOf(
        Photo(
            id = 1,
            albumId = 1,
            description = "Description 1",
            createdAt = LocalDateTime.now()
        ),
        Photo(
            id = 2,
            albumId = 1,
            description = "Description 2",
            createdAt = LocalDateTime.now()
        ),
        Photo(
            id = 3,
            albumId = 1,
            description = "Description 3",
            createdAt = LocalDateTime.now()
        ),
        Photo(
            id = 4,
            albumId = 1,
            description = "Description 4",
            createdAt = LocalDateTime.now()
        ),
        Photo(
            id = 5,
            albumId = 1,
            description = "Description 5",
            createdAt = LocalDateTime.now()
        ),
        Photo(
            id = 6,
            albumId = 2,
            description = "Description 6",
            createdAt = LocalDateTime.now()
        ),
        Photo(
            id = 7,
            albumId = 2,
            description = "Description 7",
            createdAt = LocalDateTime.now()
        ),
        Photo(
            id = 8,
            albumId = 2,
            description = "Description 8",
            createdAt = LocalDateTime.now()
        ),
        Photo(
            id = 9,
            albumId = 2,
            description = "Description 9",
            createdAt = LocalDateTime.now()
        ),
        Photo(
            id = 10,
            albumId = 2,
            description = "Description 10",
            createdAt = LocalDateTime.now()
        ),
        Photo(
            id = 11,
            albumId = 3,
            description = "Description 11",
            createdAt = LocalDateTime.now()
        ),
        Photo(
            id = 12,
            albumId = 3,
            description = "Description 12",
            createdAt = LocalDateTime.now()
        ),
        Photo(
            id = 13,
            albumId = 3,
            description = "Description 13",
            createdAt = LocalDateTime.now()
        ),
        Photo(
            id = 14,
            albumId = 3,
            description = "Description 14",
            createdAt = LocalDateTime.now()
        ),
        Photo(
            id = 15,
            albumId = 3,
            description = "Description 15",
            createdAt = LocalDateTime.now()
        ),
        Photo(
            id = 16,
            albumId = -1,
            description = "Description 16",
            createdAt = LocalDateTime.now()
        ),
        Photo(
            id = 17,
            albumId = -1,
            description = "Description 17",
            createdAt = LocalDateTime.now()
        ),
        Photo(
            id = 18,
            albumId = -1,
            description = "Description 18",
            createdAt = LocalDateTime.now()
        ),
        Photo(
            id = 19,
            albumId = -1,
            description = "Description 19",
            createdAt = LocalDateTime.now()
        ),
        Photo(
            id = 20,
            albumId = -1,
            description = "Description 20",
            createdAt = LocalDateTime.now()
        )
    )

    private fun getAlbumsWithImages(): List<AlbumWithImages> {
        return albums.map { album ->
            AlbumWithImages(
                album = album,
                photos = photos.filter { it.albumId == album.id }
            )
        }
    }

    override fun getAlbums(): Flow<List<AlbumWithImages>> {
        return flow { emit(getAlbumsWithImages().toList()) }
    }

    override fun getAlbum(albumId: Long): Flow<AlbumWithImages> {
        return flow {
            emit(getAlbumsWithImages().find { it.album.id == albumId } ?: throw IllegalArgumentException("No album found"))
        }
    }

    override fun getLatestAlbum(): Flow<AlbumWithImages> {
        return flow {
            emit(getAlbumsWithImages().last())
        }
    }

    override fun getPhoto(imageId: Long): Flow<Photo> {
        return flow {
            emit(photos.find { it.id == imageId } ?: throw IllegalArgumentException("No photo found"))
        }
    }

    override fun getPhotosWithoutAlbum(): Flow<List<Photo>> {
        return flow {
            emit(photos.filter { it.albumId < 0 })
        }
    }

    override suspend fun insertAlbum(album: Album): Long {
        val newId = (albums.size + 1).toLong()
        albums.add(
            Album(
                id = newId,
                name = album.name,
                description = album.description,
                lastChanged = LocalDateTime.now()
            )
        )
        return newId
    }

    override suspend fun updateAlbum(album: Album) {
        val index = albums.indexOfFirst { it.id == album.id }
        albums[index] = album
    }

    override suspend fun insertPhoto(photo: Photo): Long {
        val newId = (photos.size + 1).toLong()
        photos.add(
            Photo(
                id = newId,
                albumId = photo.albumId,
                description = photo.description,
                createdAt = LocalDateTime.now(),
                filePath = photo.filePath
            )
        )
        return newId
    }

    override suspend fun updatePhoto(photo: Photo) {
        val index = photos.indexOfFirst { it.id == photo.id }
        photos[index] = photo
    }

    override fun updatePhotosWithoutAlbum(albumId: Long) {
        photos.filter { it.albumId < 0 }.forEach { it.albumId = albumId }
    }

    override suspend fun deleteAlbum(albumId: Long) {
        albums.removeIf { it.id == albumId }
    }

    override suspend fun deletePhoto(photoId: Long) {
        photos.removeIf { it.id == photoId }
    }
}
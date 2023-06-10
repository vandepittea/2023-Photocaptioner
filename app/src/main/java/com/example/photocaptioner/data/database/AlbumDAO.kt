package com.example.photocaptioner.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.AlbumWithImages
import com.example.photocaptioner.model.Photo
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDAO {
    @Transaction
    @Query("SELECT * FROM albums")
    fun getAlbums(): Flow<List<AlbumWithImages>>

    @Transaction
    @Query("SELECT * FROM albums WHERE id = :id")
    fun getAlbum(id: Long): Flow<AlbumWithImages>

    @Transaction
    @Query("SELECT * FROM albums ORDER BY lastChanged DESC LIMIT 1")
    fun getLatestAlbum(): Flow<AlbumWithImages>

    @Query("SELECT * FROM photos WHERE id = :id")
    fun getPhoto(id: Long): Flow<Photo>

    @Query("SELECT * FROM photos WHERE albumId < 0")
    fun getPhotosWithoutAlbum(): Flow<List<Photo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlbum(album: Album): Long

    @Update
    suspend fun updateAlbum(album: Album)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPhoto(image: Photo): Long

    @Update
    suspend fun updatePhoto(image: Photo)

    @Query("UPDATE photos SET albumId = :albumId WHERE albumId < 0")
    fun updatePhotosWithoutAlbum(albumId: Long)
}
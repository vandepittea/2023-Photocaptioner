package com.example.photocaptioner.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(image: PhotoDB)

    @Update
    suspend fun update(image: PhotoDB)

    @Query("SELECT * FROM images WHERE albumId = :albumId")
    fun getImagesOfAlbum(albumId: Long): Flow<List<PhotoDB>>

    @Query("SELECT * FROM images WHERE id = :imageId")
    fun getImage(imageId: Long): Flow<PhotoDB>
}
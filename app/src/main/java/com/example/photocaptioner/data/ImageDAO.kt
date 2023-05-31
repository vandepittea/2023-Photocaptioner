package com.example.photocaptioner.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(image: Image)

    @Update
    suspend fun update(image: Image)

    @Query("SELECT * FROM images WHERE albumId = :albumId")
    fun getImagesOfAlbum(albumId: Long): Flow<List<Image>>

    @Query("SELECT * FROM images WHERE id = :imageId")
    fun getImage(imageId: Long): Flow<Image>
}
package com.example.photocaptioner.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(album: AlbumDB)

    @Update
    suspend fun update(album: AlbumDB)

    @Transaction
    @Query("SELECT * FROM albums")
    fun getAllAlbums(): Flow<List<AlbumWithImages>>

    @Transaction
    @Query("SELECT * FROM albums WHERE id = :id")
    fun getAlbum(id: Long): Flow<AlbumWithImages>
}
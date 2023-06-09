package com.example.photocaptioner.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.Photo

@Database(entities = [Photo::class, Album::class], version = 6, exportSchema = false)
@TypeConverters(LocalDateTimeConverter::class)
abstract class PhotoCaptionerDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDAO

    companion object {
        @Volatile
        private var INSTANCE: PhotoCaptionerDatabase? = null

        fun getDatabase(context: Context): PhotoCaptionerDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, PhotoCaptionerDatabase::class.java, "photo_captioner_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
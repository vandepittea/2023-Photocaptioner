package com.example.photocaptioner.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [PhotoDB::class, AlbumDB::class], version = 1, exportSchema = false)
@TypeConverters(LocalDateConverter::class)
abstract class PhotoCaptionerDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDAO
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
package com.example.photocaptioner.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Image::class, Album::class], version = 1)
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
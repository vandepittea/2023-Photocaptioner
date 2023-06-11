package com.example.photocaptioner.data.api

interface UnsplashRepository {
    suspend fun searchImages(query: String): List<String>
}
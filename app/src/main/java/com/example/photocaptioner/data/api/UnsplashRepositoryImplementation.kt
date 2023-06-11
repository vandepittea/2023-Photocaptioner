package com.example.photocaptioner.data.api

import com.example.photocaptioner.network.UnsplashApiClient

class UnsplashRepositoryImplementation: UnsplashRepository {
    private val unsplashService = UnsplashApiClient.unsplashService

    override suspend fun searchImages(query: String): List<String> {
        val response = unsplashService.searchPhotos(query, perPage = 10, page = 1)
        return response.results.map { it.urls.regular }
    }
}
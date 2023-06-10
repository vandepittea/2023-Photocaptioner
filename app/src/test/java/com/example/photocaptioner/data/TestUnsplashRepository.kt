package com.example.photocaptioner.data

import com.example.photocaptioner.model.UnsplashPhoto
import com.example.photocaptioner.model.UnsplashSearchResponse
import com.example.photocaptioner.model.UnsplashUrls

class TestUnsplashRepository : UnsplashRepository() {
    override suspend fun searchImages(query: String): List<String> {
        val response = UnsplashSearchResponse(
            listOf(
                UnsplashPhoto("1", UnsplashUrls("https://example.com/image1.jpg")),
                UnsplashPhoto("2", UnsplashUrls("https://example.com/image2.jpg")),
                UnsplashPhoto("3", UnsplashUrls("https://example.com/image3.jpg"))
            )
        )

        return response.results.map { it.urls.regular }
    }
}
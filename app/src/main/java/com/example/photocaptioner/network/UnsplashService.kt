package com.example.photocaptioner.network

import com.example.photocaptioner.model.UnsplashSearchResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashService {
    @Headers("Accept-Version: v1", "Authorization: Client-ID oMUwBfZwtX2-HrC3Eo1n5qPmg9NUY6EiWuWGtf_ifFM")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): UnsplashSearchResponse
}
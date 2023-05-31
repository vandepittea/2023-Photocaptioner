package com.example.photocaptioner.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val UNSPLASH_BASE_URL = "https://api.unsplash.com/"

private val okHttpClient = OkHttpClient.Builder().build()

private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(UNSPLASH_BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object UnsplashApiClient {
    val unsplashService: UnsplashService = retrofit.create(UnsplashService::class.java)
}
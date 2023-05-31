package com.example.photocaptioner.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashSearchResponse(
    @SerialName("results")
    val results: List<UnsplashPhoto>
)
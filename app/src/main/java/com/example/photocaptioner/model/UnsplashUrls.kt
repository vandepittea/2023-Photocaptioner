package com.example.photocaptioner.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashUrls(
    @SerialName("regular")
    val regular: String
)
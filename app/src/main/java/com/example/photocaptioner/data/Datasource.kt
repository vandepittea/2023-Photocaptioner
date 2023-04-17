package com.example.photocaptioner.data

import com.example.photocaptioner.R
import com.example.photocaptioner.model.Album
import com.example.photocaptioner.model.NavigationItemContent
import com.example.photocaptioner.model.Photo
import java.time.LocalDate

object Datasource {
    val defaultAlbum = getAlbums()[0]
    val defaultPhoto = defaultAlbum.photos[0]

    fun getAlbums(): List<Album> {
        return listOf(
            Album(
                R.string.album1_title,
                R.string.album1_description,
                LocalDate.of(2022, 4, 13),
                listOf(
                    Photo(R.drawable.album1_picture1, R.string.album1_picture1_description,LocalDate.of(2022, 4, 14)),
                    Photo(R.drawable.album1_picture1, R.string.album1_picture1_description,LocalDate.of(2022, 4, 14)),
                    Photo(R.drawable.album1_picture1, R.string.album1_picture1_description,LocalDate.of(2022, 4, 14))
                )
            ),
            Album(
                R.string.album2_title,
                R.string.album2_description,
                LocalDate.of(2022, 2, 26),
                listOf(
                    Photo(R.drawable.album2_picture1, R.string.album2_picture1_description,LocalDate.of(2022, 4, 14))
                )
            ),
            Album(
                R.string.album3_title,
                R.string.album3_description,
                LocalDate.of(2023, 3, 19),
                listOf(
                    Photo(R.drawable.album3_picture1, R.string.album3_picture1_description,LocalDate.of(2022, 4, 14))
                )
            ),
            Album(
                R.string.album4_title,
                R.string.album4_description,
                LocalDate.of(2022, 8, 24),
                listOf(
                    Photo(R.drawable.album4_picture1, R.string.album5_picture1_description,LocalDate.of(2022, 4, 14))
                )
            ),
            Album(
                R.string.album5_title,
                R.string.album5_description,
                LocalDate.of(2022, 12, 30),
                listOf(
                    Photo(R.drawable.album5_picture1, R.string.album5_picture1_description,LocalDate.of(2022, 4, 14))
                )
            )
        )
    }

    val navigationItemContentList = listOf(
        NavigationItemContent(
            menuItemType = MenuItemType.Home,
            icon = R.drawable.baseline_home_24,
            text = R.string.home_menu
        ),
        NavigationItemContent(
            menuItemType = MenuItemType.Photo,
            icon = R.drawable.baseline_camera_alt_24,
            text = R.string.camera_menu
        ),
        NavigationItemContent(
            menuItemType = MenuItemType.Album,
            icon = R.drawable.baseline_photo_album_24,
            text = R.string.album_menu
        )
    )
}
